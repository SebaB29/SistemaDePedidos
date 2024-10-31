package com.ing_software_grupo8.sistema_de_pedidos.controller;

import com.ing_software_grupo8.sistema_de_pedidos.DTO.AdminCreateProductRequestDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.MessageResponseDTO;
import com.ing_software_grupo8.sistema_de_pedidos.service.IProductService;

import jakarta.validation.Valid;

import com.ing_software_grupo8.sistema_de_pedidos.DTO.ProductRequestDTO;
import com.ing_software_grupo8.sistema_de_pedidos.response.GenericResponse;
import com.ing_software_grupo8.sistema_de_pedidos.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.ProductRequestDTO;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    public ResponseEntity<GenericResponse> createProduct(
            @Valid @RequestBody AdminCreateProductRequestDTO productRequest) {
        return ResponseEntity.ok(GenericResponse.builder()
                .data(productService.createProduct(productRequest))
                .message("Producto creado correctamente")
                .status(HttpStatus.OK)
                .build());
    }

    @PutMapping("/product")
    public ResponseEntity<GenericResponse> editProduct(@RequestBody ProductRequestDTO productDTO) {
        return ResponseEntity.ok(GenericResponse.builder()
                .data(productService.editProduct(productDTO))
                .message("Producto editado correctamente")
                .status(HttpStatus.OK)
                .build());
    }

    @DeleteMapping("/product")
    public ResponseEntity<GenericResponse> deleteProduct(@RequestBody ProductRequestDTO productDTO) {
        return ResponseEntity.ok(GenericResponse.builder()
                .data(productService.deleteProduct(productDTO))
                .message("Producto eliminado correctamente")
                .status(HttpStatus.OK)
                .build());
    }
}