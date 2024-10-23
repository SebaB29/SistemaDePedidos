package com.ing_software_grupo8.sistema_de_pedidos.entity;

import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.Timestamp;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private long id;

    private long userId;

    private String status;

    private Timestamp orderDate;

    private Timestamp confirmationDate;

    private List<Product> productList;
}