package com.ing_software_grupo8.sistema_de_pedidos.controller;

import com.ing_software_grupo8.sistema_de_pedidos.DTO.AdminCreateProductRequestDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.MessageResponseDTO;
import com.ing_software_grupo8.sistema_de_pedidos.entity.Product;
import com.ing_software_grupo8.sistema_de_pedidos.entity.Stock;
import com.ing_software_grupo8.sistema_de_pedidos.entity.Attribute;
import com.ing_software_grupo8.sistema_de_pedidos.service.IAttributeService;
import com.ing_software_grupo8.sistema_de_pedidos.service.IProductService;
import com.ing_software_grupo8.sistema_de_pedidos.service.IStockService;

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

    @Autowired
    private IStockService stockService;

    @Autowired
    private IAttributeService attributeService;

    private long createProduct(AdminCreateProductRequestDTO productRequest) {
        Product product = new Product();
        product.setName(productRequest.getProductName());
        product.setWeight(productRequest.getWeight());
        return productService.createProduct(product);
    }

    private void createStock(long productId, AdminCreateProductRequestDTO productRequest) {
        Stock stock = new Stock();
        stock.setProductId(productId);
        stock.setStockType(productRequest.getStockType());
        stock.setQuantity(productRequest.getQuantity());
        stockService.createStock(stock);
    }

    private void createAttribute(long productId, AdminCreateProductRequestDTO productRequest) {
        Attribute attribute = new Attribute();
        attribute.setProductId(productId);
        attribute.setDescription(productRequest.getDescription());
        attributeService.createAttribute(attribute);
    }

    public void createProductWithStockAndAttributes(
            AdminCreateProductRequestDTO productRequest) {
        long productId = createProduct(productRequest);
        createStock(productId, productRequest);
        createAttribute(productId, productRequest);
    }

    @PostMapping("/product/create")
    public ResponseEntity<MessageResponseDTO> create(
            @Valid @RequestBody AdminCreateProductRequestDTO productRequest) {
        try {
            createProductWithStockAndAttributes(productRequest);
            String messageOk = "Product created";
            MessageResponseDTO message = new MessageResponseDTO(messageOk);
            return ResponseEntity.ok(message);
        } catch (IllegalArgumentException e) {
            String messageError = "Unexpected Error";
            MessageResponseDTO message = new MessageResponseDTO(messageError);
            return ResponseEntity.badRequest().body(message);
        }
    }

    @PutMapping("/edit-product")
    public ResponseEntity<MessageResponseDTO> editProduct(@RequestBody ProductRequestDTO productDTO) {
        return ResponseEntity.ok(productService.editProduct(productDTO));
    }
}
