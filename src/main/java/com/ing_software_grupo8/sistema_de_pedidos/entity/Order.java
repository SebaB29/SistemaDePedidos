package com.ing_software_grupo8.sistema_de_pedidos.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.security.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
public class Order {
    @Id
    private long id;
    private long userId;
    private String status;
    private Timestamp orderDate;
    private Timestamp confirmationDate;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> productList;

    public Order() {}
}
