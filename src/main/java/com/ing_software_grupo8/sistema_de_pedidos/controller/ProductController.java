package com.ing_software_grupo8.sistema_de_pedidos.controller;

import com.ing_software_grupo8.sistema_de_pedidos.DTO.AdminCreateProductRequestDTO;
import com.ing_software_grupo8.sistema_de_pedidos.entity.Product;
import com.ing_software_grupo8.sistema_de_pedidos.entity.Stock;
import com.ing_software_grupo8.sistema_de_pedidos.entity.Attribute;
import com.ing_software_grupo8.sistema_de_pedidos.security.CustomUserDetails;
import com.ing_software_grupo8.sistema_de_pedidos.service.IAttributeService;
import com.ing_software_grupo8.sistema_de_pedidos.service.IProductService;
import com.ing_software_grupo8.sistema_de_pedidos.service.IStockService;

import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/product/create")
    public String create(@AuthenticationPrincipal CustomUserDetails user,
            @RequestBody AdminCreateProductRequestDTO productRequest) {
        try {
            Product product = new Product();
            product.setName(productRequest.getProductName());
            product.setWeight(productRequest.getWeight());
            long productId = productService.createProduct(user, product);

            Stock stock = new Stock();
            stock.setProductId(productId);
            stock.setStockType(productRequest.getStockType());
            stock.setQuantity(productRequest.getQuantity());
            stockService.createStock(user, stock);

            Attribute attribute = new Attribute();
            attribute.setProductId(productId);
            attribute.setDescription(productRequest.getDescription());
            attributeService.createAttribute(user, attribute);

            return String.format("Product %s created!", productRequest.getProductName());
        } catch (IllegalArgumentException e) {
            return String.format("Error!");
        }
    }
}
