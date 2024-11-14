package com.ing_software_grupo8.sistema_de_pedidos.controller;

import com.ing_software_grupo8.sistema_de_pedidos.DTO.AdminCreateProductRequestDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.MessageResponseDTO;
import com.ing_software_grupo8.sistema_de_pedidos.service.IProductService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import com.ing_software_grupo8.sistema_de_pedidos.DTO.ProductRequestDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.StockDTO;
import com.ing_software_grupo8.sistema_de_pedidos.entity.Stock;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.ProductResponseDTO;
import com.ing_software_grupo8.sistema_de_pedidos.response.GenericResponse;
import com.ing_software_grupo8.sistema_de_pedidos.service.ProductService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import org.springframework.web.bind.annotation.PostMapping;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.ProductRequestDTO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
public class ProductController {

    @Autowired
    private IProductService productService;

    @PostMapping("/product")
    public ResponseEntity<GenericResponse> createProduct(
            @Valid @RequestBody AdminCreateProductRequestDTO productRequest, HttpServletRequest request) {
        return ResponseEntity.ok(GenericResponse.builder()
                .data(productService.createProduct(productRequest, request))
                .status(HttpStatus.CREATED)
                .build());
    }

    @PutMapping("/product")
    public ResponseEntity<GenericResponse> editProduct(@RequestBody ProductRequestDTO productDTO,
            HttpServletRequest request) {
        return ResponseEntity.ok(GenericResponse.builder()
                .data(productService.editProduct(productDTO, request))
                .status(HttpStatus.OK)
                .build());
    }

    @DeleteMapping("/product")
    public ResponseEntity<GenericResponse> deleteProduct(@RequestBody ProductRequestDTO productDTO,
            HttpServletRequest request) {
        return ResponseEntity.ok(GenericResponse.builder()
                .data(productService.deleteProduct(productDTO, request))
                .status(HttpStatus.OK)
                .build());
    }

    @GetMapping("/product/{productId}/stock")
    public ResponseEntity<GenericResponse> getStock(@RequestBody ProductRequestDTO productDTO) {
        return ResponseEntity.ok(GenericResponse.builder()
                                                .data(productService.getProductStock(productDTO))
                                                .status(HttpStatus.OK)
                                                .build());
    }

    @PutMapping("/product/{productId}/stock")
    public ResponseEntity<GenericResponse> editStock(@RequestBody StockDTO stockDTO, HttpServletRequest request) {
        return ResponseEntity.ok(GenericResponse.builder()
                .data(productService.editStock(stockDTO, request))
                .status(HttpStatus.OK)
                .build());
    }

    @GetMapping("/product")
    public ResponseEntity<GenericResponse> getAllProducts() {
        List<ProductResponseDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(GenericResponse.builder()
                .data(products)
                .status(HttpStatus.OK)
                .build());
    }
}