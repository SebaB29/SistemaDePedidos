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
    private Long stockId;

    @Column(name = "stock_type", nullable = false)
    private String stockType;

    @Column(nullable = false)
    private Float quantity;

    public boolean discountStock(Float stockQuantity) {
        if (quantity < stockQuantity) return false;
        quantity -= stockQuantity;
        return true;
    }

    public boolean addStock(Float stockQuantity) {
        quantity += stockQuantity;
        return true;
    }
}