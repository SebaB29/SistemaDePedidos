package com.ing_software_grupo8.sistema_de_pedidos.controller;

import com.ing_software_grupo8.sistema_de_pedidos.DTO.MessageResponseDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.ProductRequestDTO;
import com.ing_software_grupo8.sistema_de_pedidos.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @PutMapping("/edit-product")
    public ResponseEntity<MessageResponseDTO> editProduct(@RequestBody ProductRequestDTO productDTO) {
        return ResponseEntity.ok(productService.editProduct(productDTO));
    }
}
