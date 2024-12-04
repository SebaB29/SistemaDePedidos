package com.ing_software_grupo8.sistema_de_pedidos.repository;

import com.ing_software_grupo8.sistema_de_pedidos.entity.Order;
import com.ing_software_grupo8.sistema_de_pedidos.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUser(User user);
}
