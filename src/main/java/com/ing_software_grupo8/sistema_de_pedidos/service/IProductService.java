package com.ing_software_grupo8.sistema_de_pedidos.service;

import com.ing_software_grupo8.sistema_de_pedidos.DTO.*;
import com.ing_software_grupo8.sistema_de_pedidos.entity.Stock;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Optional;

public interface IProductService {

    MessageResponseDTO createProduct(AdminCreateProductRequestDTO productRequest, HttpServletRequest request);

    MessageResponseDTO editProduct(ProductRequestDTO productDTO, HttpServletRequest request);

    MessageResponseDTO deleteProduct(Long productId, HttpServletRequest request);

    List<ProductResponseDTO> getAllProducts();

    MessageResponseDTO editStock(StockDTO stockDTO, HttpServletRequest request);

    Optional<Stock> getProductStock(Long productId);
}
