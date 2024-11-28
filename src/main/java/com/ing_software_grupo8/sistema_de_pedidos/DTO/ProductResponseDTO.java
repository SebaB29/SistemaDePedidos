package com.ing_software_grupo8.sistema_de_pedidos.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {
    @JsonProperty
    private Long productId;

    @JsonProperty
    private String name;

    @JsonProperty
    private Float quantity;

    @JsonProperty(value = "price")
    private float price;


    @JsonProperty
    private List<AttributeDTO> attributes;
}
