package com.ing_software_grupo8.sistema_de_pedidos.controller;

import com.ing_software_grupo8.sistema_de_pedidos.DTO.AdminCreateProductRequestDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.MessageResponseDTO;
import com.ing_software_grupo8.sistema_de_pedidos.service.IProductService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.ProductRequestDTO;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

@RestController
@Validated
public class ProductController {

    @Autowired
    private IProductService productService;

    @PostMapping("/product")
    public ResponseEntity<MessageResponseDTO> create(
            @Valid @RequestBody AdminCreateProductRequestDTO productRequest) {
        return ResponseEntity.ok(productService.createProduct(productRequest));
    }

    @PutMapping("/product")
    public ResponseEntity<MessageResponseDTO> editProduct(@RequestBody ProductRequestDTO productDTO) {
        return ResponseEntity.ok(productService.editProduct(productDTO));
    }
}
