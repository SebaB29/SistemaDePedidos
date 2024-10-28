package com.ing_software_grupo8.sistema_de_pedidos.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderState {
    @Id
    private long orderStateId;

    @Column(unique=true)
    private int stateCode;

    @Column(unique=true)
    private String stateDesc;
}
