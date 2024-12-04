package com.ing_software_grupo8.sistema_de_pedidos.repository;

import com.ing_software_grupo8.sistema_de_pedidos.entity.ProductOrder;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProductOrderRepository extends JpaRepository<ProductOrder, Long> {
    List<ProductOrder> findByProduct_ProductId(Long productId);
}
