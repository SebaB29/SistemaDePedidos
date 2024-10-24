package com.ing_software_grupo8.sistema_de_pedidos.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductOrder {
    private long productOrderId;
    private long orderId;
    private long productId;
    private float orderQuantity;
}
