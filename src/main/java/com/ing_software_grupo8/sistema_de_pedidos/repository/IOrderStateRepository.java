package com.ing_software_grupo8.sistema_de_pedidos.repository;

import com.ing_software_grupo8.sistema_de_pedidos.entity.OrderState;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderStateRepository extends JpaRepository<OrderState, Long> {
    OrderState findByStateCode(int stateCode);
}
