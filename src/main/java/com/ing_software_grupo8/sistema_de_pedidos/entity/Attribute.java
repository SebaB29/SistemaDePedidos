package com.ing_software_grupo8.sistema_de_pedidos.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Attribute {

    private long id;

    private long productId;

    private String description;

}
