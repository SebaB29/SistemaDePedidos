package com.ing_software_grupo8.sistema_de_pedidos.service;

import com.ing_software_grupo8.sistema_de_pedidos.entity.Product;
import com.ing_software_grupo8.sistema_de_pedidos.repository.IProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService implements IProductService {

    @Autowired
    private IProductRepository productRepository;

    public long createProduct(Product product) {
        // if (!user.isAdmin())
        // throw new IllegalArgumentException("Only admins can create products");
        Product productSaved = productRepository.save(product);
        return productSaved.getId();
    }
}
