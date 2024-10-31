package com.ing_software_grupo8.sistema_de_pedidos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long stockId;

    @Column(name = "stock_type", nullable = false)
    private String stockType;

    @Column(nullable = false)
    private float quantity;
}
