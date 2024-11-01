package com.ing_software_grupo8.sistema_de_pedidos.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Stock {
    private long stockId;
    private String stockType;
    private long productId;
    private float quantity;
}
