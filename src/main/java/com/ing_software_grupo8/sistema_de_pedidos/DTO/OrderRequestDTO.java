package com.ing_software_grupo8.sistema_de_pedidos.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {

    @JsonProperty(required = true)
    private Long userId;

    @JsonProperty(required = true)
    private List<ProductOrderDTO> productOrderDTOList;
}
