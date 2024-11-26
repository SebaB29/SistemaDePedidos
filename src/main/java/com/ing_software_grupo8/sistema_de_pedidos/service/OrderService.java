package com.ing_software_grupo8.sistema_de_pedidos.service;

import com.ing_software_grupo8.sistema_de_pedidos.DTO.*;
import com.ing_software_grupo8.sistema_de_pedidos.entity.*;
import com.ing_software_grupo8.sistema_de_pedidos.exception.ApiException;
import com.ing_software_grupo8.sistema_de_pedidos.repository.IOrderRepository;
import com.ing_software_grupo8.sistema_de_pedidos.repository.IOrderStateRepository;
import com.ing_software_grupo8.sistema_de_pedidos.repository.IProductRepository;
import com.ing_software_grupo8.sistema_de_pedidos.repository.IUserRepository;
import com.ing_software_grupo8.sistema_de_pedidos.rules.RuleManager;
import com.ing_software_grupo8.sistema_de_pedidos.utils.OrderStateEnum;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ing_software_grupo8.sistema_de_pedidos.rules.RuleManager;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private IOrderRepository orderRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private IOrderStateRepository orderStateRepository;

    @Autowired
    private IJwtService jwtService;

    @Autowired
    private RuleManager ruleManager;

    @Override
    public MessageResponseDTO create(OrderRequestDTO orderRequestDTO, HttpServletRequest request) {
        validateOrderProducts(orderRequestDTO.getProductOrderDTOList());

        Order order = new Order();
        order.setUser(findUserById(orderRequestDTO.getUserId()));
        order.setOrderDate(LocalDateTime.now());
        order.setOrderState(findOrderStateByCode(OrderStateEnum.CONFIRMADO.ordinal()));

        List<ProductOrder> productOrders = orderRequestDTO.getProductOrderDTOList().stream()
                .map(dto -> createProductOrder(dto, order))
                .collect(Collectors.toList());

        order.setProductOrder(productOrders);

        if (!ruleManager.validateOrder(order)) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "La orden no cumple con las reglas de negocio");
        }

        orderRepository.save(order);

        return new MessageResponseDTO("Orden creada correctamente");
    }

    @Override
    public MessageResponseDTO updateState(OrderRequestDTO orderRequestDTO, HttpServletRequest request) {
        Order order = findOrderById(orderRequestDTO.getOrderId());
        OrderState orderState = findOrderStateByCode(orderRequestDTO.getOrderState());

        if (orderState.getStateCode() == OrderStateEnum.CANCELADO.ordinal()) {
            if (jwtService.isSameUser(order.getUser(), jwtService.getTokenFromRequest(request))) {
                cancelOrder(order);
            }else{
                throw new ApiException(HttpStatus.UNAUTHORIZED, "La orden no te pertenece");
            }
        } else {
            validateAdminAuthorization(request);
        }

        order.setOrderState(orderState);
        orderRepository.save(order);

        return new MessageResponseDTO("Se actualizó el estado de la orden a: " + order.getOrderState().getStateDesc());
    }

    @Override
    public OrderListDTO getAll(Long userId, HttpServletRequest request) {
        if (userId == null) {
            validateAdminAuthorization(request);
        } else {
            validateUserAuthorization(request);
        }
        List<Order> orders = userId == null ? orderRepository.findAll() : orderRepository.findAllByUser(findUserById(userId));
        List<OrderResponseDTO> orderResponseDTOList = orders.stream()
                .map(this::mapToOrderResponseDTO)
                .collect(Collectors.toList());

        return new OrderListDTO(orderResponseDTOList);
    }

    // Métodos auxiliares

    private void validateOrderProducts(List<ProductOrderDTO> productOrderDTOList) {
        if (productOrderDTOList.isEmpty()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Debes agregar al menos un producto a la orden.");
        }

        for (ProductOrderDTO productOrderDTO : productOrderDTOList) {
            Product product = findProductById(productOrderDTO.getProductId());
            if (product.getStock().getQuantity() < productOrderDTO.getQuantity()) {
                throw new ApiException(HttpStatus.BAD_REQUEST, "El stock disponible es menor al solicitado para el producto.");
            }
        }
    }

    private void validateAdminAuthorization(HttpServletRequest request) {
        if (!jwtService.tokenHasRoleAdmin(request)) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "No tienes autorización");
        }
    }

    private void validateUserAuthorization(HttpServletRequest request) {
        if (!jwtService.tokenHasRoleUser(request)) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "No tienes autorización");
        }
    }

    private void validateCancelableOrder(Order order) {
        if (order.getOrderState().getStateCode() != OrderStateEnum.CONFIRMADO.ordinal()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "La orden no puede ser cancelada porque no está confirmada.");
        }
        if (Duration.between(order.getOrderDate(), LocalDateTime.now()).toHours() >= 24) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "La orden fue creada hace más de 24 horas.");
        }
    }

    private ProductOrder createProductOrder(ProductOrderDTO productOrderDTO, Order order) {
        Product product = findProductById(productOrderDTO.getProductId());
        discountStock(product, productOrderDTO.getQuantity());

        ProductOrder productOrder = new ProductOrder();
        productOrder.setOrderQuantity(productOrderDTO.getQuantity());
        productOrder.setProduct(product);
        productOrder.setOrder(order);

        return productOrder;
    }

    private void discountStock(Product product, float quantity) {
        if (!product.getStock().discountStock(quantity)) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "El stock disponible es menor al solicitado.");
        }
    }

    private void cancelOrder(Order order) {
        validateCancelableOrder(order);

        order.getProductOrder().forEach(productOrder -> {
            Product product = productOrder.getProduct();
            product.getStock().addStock(productOrder.getOrderQuantity());
        });
    }

    private OrderResponseDTO mapToOrderResponseDTO(Order order) {
        return new OrderResponseDTO(
                order.getOrderId(),
                order.getOrderState().getStateDesc(),
                order.getOrderDate().toString(),
                order.getProductOrder().stream()
                        .map(this::mapToProductResponseDTO)
                        .collect(Collectors.toList())
        );
    }

    private ProductResponseDTO mapToProductResponseDTO(ProductOrder productOrder) {
        List<AttributeDTO> attributes = productOrder.getProduct().getAttributes().stream()
                .map(attr -> new AttributeDTO(attr.getDescription(), attr.getValue()))
                .collect(Collectors.toList());

        return new ProductResponseDTO(
                productOrder.getProduct().getProductId(),
                productOrder.getProduct().getName(),
                productOrder.getOrderQuantity(),
                attributes
        );
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "Usuario no encontrado."));
    }

    private Product findProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "Producto no encontrado."));
    }

    private Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "La orden no existe."));
    }

    private OrderState findOrderStateByCode(int stateCode) {
        return Optional.ofNullable(orderStateRepository.findByStateCode(stateCode))
                .orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "El estado a modificar es inválido."));
    }
}
