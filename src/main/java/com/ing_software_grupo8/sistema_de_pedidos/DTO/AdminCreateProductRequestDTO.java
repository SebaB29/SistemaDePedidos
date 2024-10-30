package com.ing_software_grupo8.sistema_de_pedidos.DTO;

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

    private String productName;

    private String stockType;

    private float quantity;

    private String description;

    private int weight;
}
