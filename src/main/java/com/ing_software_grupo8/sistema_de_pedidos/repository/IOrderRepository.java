package com.ing_software_grupo8.sistema_de_pedidos.repository;

import com.ing_software_grupo8.sistema_de_pedidos.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderRepository extends JpaRepository<Order, Long> {
}
