package com.ing_software_grupo8.sistema_de_pedidos.controller;

import com.ing_software_grupo8.sistema_de_pedidos.DTO.MessageResponseDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.ProductRequestDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.StockDTO;
import com.ing_software_grupo8.sistema_de_pedidos.entity.Stock;
import com.ing_software_grupo8.sistema_de_pedidos.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @PutMapping("/product")
    public ResponseEntity<MessageResponseDTO> editProduct(@RequestBody ProductRequestDTO productDTO) {
        return ResponseEntity.ok(productService.editProduct(productDTO));
    }
    @DeleteMapping("/product")
    public ResponseEntity<MessageResponseDTO> deleteProduct(@RequestBody ProductRequestDTO productDTO) {
        return ResponseEntity.ok(productService.deleteProduct(productDTO));
    }
    @GetMapping("/product/{productId}/stock")
    public ResponseEntity<GenericResponse> getStock(@RequestBody ProductRequestDTO productDTO) {
        return ResponseEntity.ok(GenericResponse.builder()
                .data(productService.getProductStock(productDTO))
                .message("Stock del producto obtenido correctamente")
                .status(HttpStatus.OK)
                .build());
    }
    @PutMapping("/product/{productId}/stock")
    public ResponseEntity<GenericResponse> updateStock(@RequestBody StockDTO stockDTO) {
        return ResponseEntity.ok(GenericResponse.builder()
                .data(productService.editStock(stockDTO))
                .message("Stock del producto actualizado correctamente")
                .status(HttpStatus.NO_CONTENT) // El status 204 indica que no hay contenido adicional
                .build());
    }
}
