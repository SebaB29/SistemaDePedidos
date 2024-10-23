package com.ing_software_grupo8.sistema_de_pedidos.entity;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Entity
@Getter
@Setter
public class Product {
    @Id
    private long Id;
    private String name;
    private int weight;
    @ElementCollection
    private HashMap<String, String> attributes;

    public Product() {}
}
