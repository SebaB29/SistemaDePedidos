package com.ing_software_grupo8.sistema_de_pedidos.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "customer_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_state_id", nullable = false)
    private OrderState orderState;

    @Column(nullable = false)
    private LocalTime orderDate;

    @Column
    private LocalTime confirmationDate;

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "order")
    private List<ProductOrder> productOrder;
}