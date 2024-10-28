package com.ing_software_grupo8.sistema_de_pedidos.entity;

import com.ing_software_grupo8.sistema_de_pedidos.DTO.ProductOrderDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.Timestamp;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Order {

    @Id
    private long id;

    @Column(unique = true)
    private long userId;

    @Column
    private OrderState status;

    @Column
    private LocalTime orderDate;

    private LocalTime confirmationDate;

    @OneToMany
    private List<ProductOrder> productOrder;
}