package com.ing_software_grupo8.sistema_de_pedidos.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTO {

    @JsonProperty(value = "product_id", required = true)
    private Long productId;

    @JsonProperty(value = "product_name")
    private String name;

    @JsonProperty(value = "price")
    private float price;

    @JsonProperty(value = "attributes")
    private List<AttributeDTO> attributes;
}