package com.ing_software_grupo8.sistema_de_pedidos.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTO {
    private long productId;
    private String name;
    private int weight;
    private List<AttributeDTO> attributes;
}