package com.ing_software_grupo8.sistema_de_pedidos.service;

import com.ing_software_grupo8.sistema_de_pedidos.DTO.MessageResponseDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.OrderRequestDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.ProductOrderDTO;
import com.ing_software_grupo8.sistema_de_pedidos.entity.Order;
import com.ing_software_grupo8.sistema_de_pedidos.entity.ProductOrder;
import com.ing_software_grupo8.sistema_de_pedidos.repository.IOrderRepository;
import com.ing_software_grupo8.sistema_de_pedidos.repository.IOrderStateRepository;
import com.ing_software_grupo8.sistema_de_pedidos.repository.IProductRepository;
import com.ing_software_grupo8.sistema_de_pedidos.repository.IUserRepository;
import com.ing_software_grupo8.sistema_de_pedidos.utils.OrderStateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService implements IOrderService{

    @Autowired
    IOrderRepository orderRepository;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IProductRepository productRepository;

    @Autowired
    IOrderStateRepository orderStateRepository;


    public MessageResponseDTO create(OrderRequestDTO orderRequestDTO) {
        validateOrder(orderRequestDTO);

        Order order = new Order();

        order.setUserId(orderRequestDTO.getUserId());
        order.setOrderDate(LocalTime.now());
        order.setOrderState(orderStateRepository.findByStateCode(OrderStateEnum.CREADO.ordinal()));
        List<ProductOrder> productOrderList = new ArrayList<>();
        for(ProductOrderDTO productOrderDTO : orderRequestDTO.getProductOrderDTOList()){
            ProductOrder productOrder = new ProductOrder();
            productOrder.setOrderQuantity(productOrderDTO.getQuantity());
            productOrder.setProductId(productOrderDTO.getProductId());
            productOrderList.add(productOrder);
        }
        order.setProductOrder(productOrderList);

        orderRepository.save(order);

        return new MessageResponseDTO("Orden creada correctamente");
    }

    private void validateOrder(OrderRequestDTO orderRequestDTO){
        if(!userRepository.existsById(orderRequestDTO.getUserId())) throw new IllegalArgumentException("Usuario no encontrado");

        for(ProductOrderDTO productOrderDTO : orderRequestDTO.getProductOrderDTOList()){
            if(!productRepository.existsById(productOrderDTO.getProductId())) throw new IllegalArgumentException("Product no encontrado");

            //TO DO VALIDAR STOCK
        }
    }
}
