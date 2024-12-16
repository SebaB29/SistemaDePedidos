package com.ing_software_grupo8.sistema_de_pedidos.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_state_id", nullable = false)
    private OrderState orderState;

    @Column(nullable = false)
    private LocalDateTime orderDate;

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "order", cascade = CascadeType.ALL)
    private List<ProductOrder> productOrder;
}