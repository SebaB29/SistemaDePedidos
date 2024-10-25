package com.ing_software_grupo8.sistema_de_pedidos.controller;

import com.ing_software_grupo8.sistema_de_pedidos.DTO.AdminCreateProductRequestDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.MessageResponseDTO;
import com.ing_software_grupo8.sistema_de_pedidos.entity.Product;
import com.ing_software_grupo8.sistema_de_pedidos.entity.Stock;
import com.ing_software_grupo8.sistema_de_pedidos.entity.Attribute;
import com.ing_software_grupo8.sistema_de_pedidos.security.CustomUserDetails;
import com.ing_software_grupo8.sistema_de_pedidos.service.IAttributeService;
import com.ing_software_grupo8.sistema_de_pedidos.service.IProductService;
import com.ing_software_grupo8.sistema_de_pedidos.service.IStockService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@RestController
public class ProductController {

    @Autowired
    private IProductService productService;

    @Autowired
    private IStockService stockService;

    @Autowired
    private IAttributeService attributeService;

    private long createProduct(CustomUserDetails user, AdminCreateProductRequestDTO productRequest) {
        Product product = new Product();
        product.setName(productRequest.getProductName());
        product.setWeight(productRequest.getWeight());
        return productService.createProduct(user, product);
    }

    private void createStock(long productId, CustomUserDetails user, AdminCreateProductRequestDTO productRequest) {
        Stock stock = new Stock();
        stock.setProductId(productId);
        stock.setStockType(productRequest.getStockType());
        stock.setQuantity(productRequest.getQuantity());
        stockService.createStock(user, stock);
    }

    private void createAttribute(long productId, CustomUserDetails user, AdminCreateProductRequestDTO productRequest) {
        Attribute attribute = new Attribute();
        attribute.setProductId(productId);
        attribute.setDescription(productRequest.getDescription());
        attributeService.createAttribute(user, attribute);
    }

    public void createProductWithStockAndAttributes(CustomUserDetails user,
            AdminCreateProductRequestDTO productRequest) {
        long productId = createProduct(user, productRequest);
        createStock(productId, user, productRequest);
        createAttribute(productId, user, productRequest);
    }

    @PostMapping("/product/create")
    public ResponseEntity<MessageResponseDTO> create(@AuthenticationPrincipal CustomUserDetails user,
            @RequestBody AdminCreateProductRequestDTO productRequest) {
        try {
            createProductWithStockAndAttributes(user, productRequest);
            String messageOk = "Product created";
            MessageResponseDTO message = new MessageResponseDTO(messageOk);
            return ResponseEntity.ok(message);
        } catch (IllegalArgumentException e) {
            String messageError = "Unexpected Error";
            MessageResponseDTO message = new MessageResponseDTO(messageError);
            return ResponseEntity.badRequest().body(message);
        }
    }
}
