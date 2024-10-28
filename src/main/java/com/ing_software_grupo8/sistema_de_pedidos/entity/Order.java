package com.ing_software_grupo8.sistema_de_pedidos.entity;

import jakarta.persistence.*;
import jdk.jfr.Relational;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Order {

    @Id
    private long id;

    @Column(unique = true)
    private long userId;

    @ManyToOne
    @JoinColumn(name = "order_state")
    private OrderState orderState;

    @Column(nullable = false)
    private LocalTime orderDate;

    @Column
    private LocalTime confirmationDate;

    @OneToMany
    private List<ProductOrder> productOrder;
}