package com.ing_software_grupo8.sistema_de_pedidos.service;

import com.ing_software_grupo8.sistema_de_pedidos.DTO.*;
import com.ing_software_grupo8.sistema_de_pedidos.entity.Stock;

import java.util.List;
import java.util.Optional;

public interface IProductService {

    MessageResponseDTO createProduct(AdminCreateProductRequestDTO productRequest);

    MessageResponseDTO editProduct(ProductRequestDTO productDTO);

    MessageResponseDTO deleteProduct(Long productId);

    List<ProductResponseDTO> getAllProducts();

    MessageResponseDTO editStock(StockDTO stockDTO);

    Optional<Stock> getProductStock(Long productId);
}
