package com.ing_software_grupo8.sistema_de_pedidos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderStateId;

    @Column
    private int stateCode;

    @Column
    private String stateDesc;
}
