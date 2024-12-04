package com.ing_software_grupo8.sistema_de_pedidos.repository;

import com.ing_software_grupo8.sistema_de_pedidos.entity.Product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByName(String name);
}
