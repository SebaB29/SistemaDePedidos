package com.ing_software_grupo8.sistema_de_pedidos.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductOrderDTO {
    @JsonProperty(value = "product_id")
    private Long productId;
    @JsonProperty(value = "quantity")
    private float quantity;
}
