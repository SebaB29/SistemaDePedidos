package com.ing_software_grupo8.sistema_de_pedidos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ing_software_grupo8.sistema_de_pedidos.entity.ProductOrder;

public interface IProductOrderRepository extends JpaRepository<ProductOrder, Long> {

    List<ProductOrder> findByProduct_ProductId(Long productId);
}
