package com.ing_software_grupo8.sistema_de_pedidos.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ing_software_grupo8.sistema_de_pedidos.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.management.ConstructorParameters;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {

    @JsonProperty
    private String name;

    @JsonProperty
    private List<AttributeDTO> attributes;

    @JsonProperty
    private Float quantity;

    public ProductResponseDTO(String name, List<AttributeDTO> attributes) {
        this.name = name;
        this.attributes = attributes;
    }
}
