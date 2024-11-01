package com.ing_software_grupo8.sistema_de_pedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ing_software_grupo8.sistema_de_pedidos.entity.Stock;

public interface IStockRepository extends JpaRepository<Stock, Long> {
}
