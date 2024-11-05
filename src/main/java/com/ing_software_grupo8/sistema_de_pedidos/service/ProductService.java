package com.ing_software_grupo8.sistema_de_pedidos.service;

import com.ing_software_grupo8.sistema_de_pedidos.DTO.*;
import com.ing_software_grupo8.sistema_de_pedidos.entity.Attribute;
import com.ing_software_grupo8.sistema_de_pedidos.entity.Product;
import com.ing_software_grupo8.sistema_de_pedidos.entity.Stock;
import com.ing_software_grupo8.sistema_de_pedidos.exception.ApiException;
import com.ing_software_grupo8.sistema_de_pedidos.repository.IProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService implements IProductService {

    @Autowired
    private IProductRepository productRepository;

    private Stock createStock(AdminCreateProductRequestDTO productRequest) {
        Stock stock = new Stock();
        stock.setStockType(productRequest.getStockType());
        stock.setQuantity(productRequest.getQuantity());
        return stock;
    }

    public MessageResponseDTO createProduct(AdminCreateProductRequestDTO productRequest) {
        Stock stock = createStock(productRequest);
        Product product = new Product();
        product.setName(productRequest.getProductName());
        product.setStock(stock);
        List<Attribute> attributes = productRequest.getAttributes().stream()
                .map(attributeDTO -> {
                    Attribute attribute = new Attribute();
                    attribute.setDescription(attributeDTO.getDescription());
                    attribute.setProduct(product);
                    return attribute;
                })
                .collect(Collectors.toList());
        product.setAttributes(attributes);
        productRepository.save(product);
        return new MessageResponseDTO("Producto creado");
    }

    public MessageResponseDTO editProduct(ProductRequestDTO productDTO) {

        Product product = productRepository.findById(productDTO.getProductId())
                .orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "Producto no encontrado"));

        product.setName(productDTO.getName());

        List<Attribute> attributes = productDTO.getAttributes().stream()
                .map(attributeDTO -> {
                    Attribute attribute = new Attribute();
                    attribute.setDescription(attributeDTO.getDescription());
                    attribute.setProduct(product);
                    return attribute;
                })
                .collect(Collectors.toList());

        product.setAttributes(attributes);

        productRepository.save(product);

        return new MessageResponseDTO("Producto editado correctamente");
    }

    public MessageResponseDTO deleteProduct(ProductRequestDTO productDTO) {

        Product product = productRepository.findById(productDTO.getProductId())
                .orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "Producto no encontrado"));

        productRepository.delete(product);
        return new MessageResponseDTO("Producto eliminado correctamente");
    }

    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(product -> new ProductResponseDTO(
                        product.getName(),
                        product.getAttributes().stream()
                                .map(attribute -> new AttributeDTO(attribute.getDescription(), attribute.getValue()))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }
}