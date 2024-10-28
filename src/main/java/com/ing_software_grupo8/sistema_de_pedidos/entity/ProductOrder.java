package com.ing_software_grupo8.sistema_de_pedidos.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProductOrder {
    @Id
    private long productOrderId;

    @Column
    private long orderId;

    @Column
    private long productId;

    @Column
    private float orderQuantity;
}
