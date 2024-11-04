package com.ing_software_grupo8.sistema_de_pedidos.service;

import com.ing_software_grupo8.sistema_de_pedidos.DTO.AdminCreateProductRequestDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.MessageResponseDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.ProductRequestDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.ProductResponseDTO;

import java.util.List;

public interface IProductService {

    MessageResponseDTO createProduct(AdminCreateProductRequestDTO productRequest);

    MessageResponseDTO editProduct(ProductRequestDTO productDTO);

    MessageResponseDTO deleteProduct(ProductRequestDTO productDTO);

    List<ProductResponseDTO> getAllProducts();
}
