package com.ing_software_grupo8.sistema_de_pedidos.service;

import com.ing_software_grupo8.sistema_de_pedidos.DTO.AdminCreateProductRequestDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.MessageResponseDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.ProductRequestDTO;

public interface IProductService {

    void createProduct(AdminCreateProductRequestDTO productRequest);

    MessageResponseDTO editProduct(ProductRequestDTO productDTO);
}
