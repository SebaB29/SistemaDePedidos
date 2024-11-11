package com.ing_software_grupo8.sistema_de_pedidos.service;

import com.ing_software_grupo8.sistema_de_pedidos.DTO.MessageResponseDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.OrderListDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.OrderRequestDTO;

public interface IOrderService {

    MessageResponseDTO create(OrderRequestDTO orderRequestDTO);

    OrderListDTO getAll(Long userId);
}
