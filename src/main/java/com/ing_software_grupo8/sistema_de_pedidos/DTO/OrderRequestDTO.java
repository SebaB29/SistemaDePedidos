package com.ing_software_grupo8.sistema_de_pedidos.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {

    private Long userId;

    private List<ProductOrderDTO> productOrderDTOList;
}
