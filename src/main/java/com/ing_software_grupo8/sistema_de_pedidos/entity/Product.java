package com.ing_software_grupo8.sistema_de_pedidos.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

import java.util.HashMap;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {

    @Id
    private long Id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int weight;
}