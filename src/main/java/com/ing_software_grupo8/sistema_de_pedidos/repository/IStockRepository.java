package com.ing_software_grupo8.sistema_de_pedidos.repository;

import com.ing_software_grupo8.sistema_de_pedidos.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IStockRepository extends JpaRepository<Stock, Long> {
}
