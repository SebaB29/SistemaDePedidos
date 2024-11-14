package com.ing_software_grupo8.sistema_de_pedidos.service;

import com.ing_software_grupo8.sistema_de_pedidos.DTO.*;
import com.ing_software_grupo8.sistema_de_pedidos.entity.*;
import com.ing_software_grupo8.sistema_de_pedidos.exception.ApiException;
import com.ing_software_grupo8.sistema_de_pedidos.repository.IOrderRepository;
import com.ing_software_grupo8.sistema_de_pedidos.repository.IOrderStateRepository;
import com.ing_software_grupo8.sistema_de_pedidos.repository.IProductRepository;
import com.ing_software_grupo8.sistema_de_pedidos.repository.IUserRepository;
import com.ing_software_grupo8.sistema_de_pedidos.utils.OrderStateEnum;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IOrderService {

    @Autowired
    IOrderRepository orderRepository;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IProductRepository productRepository;

    @Autowired
    IOrderStateRepository orderStateRepository;

    @Autowired
    IJwtService jwtService;

    public OrderListDTO getAll(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Usuario no encontrado.");
        }
        List<Order> orders = orderRepository.findAllByUser(user.get());

        List<OrderResponseDTO> orderResponseDTOList = new ArrayList<>();
        for (Order order : orders) {
            OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
            orderResponseDTO.setOrderState(order.getOrderState().getStateDesc());
            orderResponseDTO.setCreationDate(order.getOrderDate().toString());
            if (order.getConfirmationDate() != null)
                orderResponseDTO.setConfimationDate(order.getOrderDate().toString());
            orderResponseDTO.setProductDTOList(getOrderProductListDTO(order));
            orderResponseDTOList.add(orderResponseDTO);
        }

        return new OrderListDTO(orderResponseDTOList);
    }

    public MessageResponseDTO create(OrderRequestDTO orderRequestDTO, HttpServletRequest request) {
        validateOrder(orderRequestDTO, request);

        Order order = new Order();

        order.setUser(userRepository.findById(orderRequestDTO.getUserId()).get());
        order.setOrderDate(LocalTime.now());
        order.setOrderState(orderStateRepository.findByStateCode(OrderStateEnum.CREADO.ordinal()));
        List<ProductOrder> productOrderList = new ArrayList<>();
        for (ProductOrderDTO productOrderDTO : orderRequestDTO.getProductOrderDTOList()) {
            Product product = productRepository.findById(productOrderDTO.getProductId()).get();
            validateStock(product, productOrderDTO.getQuantity());
            ProductOrder productOrder = new ProductOrder();
            productOrder.setOrderQuantity(productOrderDTO.getQuantity());
            productOrder.setProduct(product);
            productOrderList.add(productOrder);
        }
        order.setProductOrder(productOrderList);

        orderRepository.save(order);

        return new MessageResponseDTO("Orden creada correctamente");
    }

    private List<ProductResponseDTO> getOrderProductListDTO(Order order) {
        List<ProductResponseDTO> productResponseDTOList = new ArrayList<>();
        for (ProductOrder productOrder : order.getProductOrder()) {
            ProductResponseDTO productResponseDTO = new ProductResponseDTO();
            productResponseDTO.setQuantity(productOrder.getOrderQuantity());
            productResponseDTO.setName(productOrder.getProduct().getName());
            List<AttributeDTO> attributeDTOListt = new ArrayList<>();
            for (Attribute attribute : productOrder.getProduct().getAttributes()) {
                AttributeDTO attributeDTO = new AttributeDTO();
                attributeDTO.setDescription(attribute.getDescription());
                attributeDTO.setValue(attribute.getValue());
                attributeDTOListt.add(attributeDTO);
            }
            productResponseDTO.setAttributes(attributeDTOListt);
            productResponseDTOList.add(productResponseDTO);
        }
        return productResponseDTOList;
    }

    private void validateOrder(OrderRequestDTO orderRequestDTO, HttpServletRequest request) {
        if (!jwtService.tokenHasRoleAdmin(request))
            throw new ApiException(HttpStatus.UNAUTHORIZED, "No tienes autorizacion");
        if (!userRepository.existsById(orderRequestDTO.getUserId()))
            throw new ApiException(HttpStatus.BAD_REQUEST, "Usuario no encontrado.");

        for (ProductOrderDTO productOrderDTO : orderRequestDTO.getProductOrderDTOList()) {
            if (!productRepository.existsById(productOrderDTO.getProductId()))
                throw new ApiException(HttpStatus.BAD_REQUEST, "Product no encontrado.");
            if (productOrderDTO.getQuantity() <= 0)
                throw new ApiException(HttpStatus.BAD_REQUEST, "La cantidad debe ser mayor a 0");
        }
    }

    private void validateStock(Product product, float orderQuantity) {
        if (product.getStock().getQuantity() < orderQuantity)
            throw new ApiException(HttpStatus.BAD_REQUEST, "El stock disponible es menor al solicitado.");
    }
}