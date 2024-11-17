package com.ing_software_grupo8.sistema_de_pedidos.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminCreateProductRequestDTO {

    private long adminId;

    @JsonProperty(value = "product_name")
    private String productName;

    @JsonProperty(value = "stock_type")
    private String stockType;

    @JsonProperty(value = "quantity")
    private float quantity;

    @JsonProperty(value = "attributes")
    private List<AttributeDTO> attributes;
}
