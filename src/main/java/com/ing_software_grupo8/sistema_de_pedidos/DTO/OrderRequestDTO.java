package com.ing_software_grupo8.sistema_de_pedidos.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {
    @JsonProperty(value = "user_id")
    private Long userId;

    @JsonProperty(value = "product_order_dto_list")
    private List<ProductOrderDTO> productOrderDTOList;
}
