package com.ing_software_grupo8.sistema_de_pedidos.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ing_software_grupo8.sistema_de_pedidos.DTO.AdminCreateProductRequestDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.AttributeDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.MessageResponseDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.ProductRequestDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.ProductResponseDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.StockDTO;
import com.ing_software_grupo8.sistema_de_pedidos.entity.Attribute;
import com.ing_software_grupo8.sistema_de_pedidos.entity.Product;
import com.ing_software_grupo8.sistema_de_pedidos.entity.Stock;
import com.ing_software_grupo8.sistema_de_pedidos.exception.ApiException;
import com.ing_software_grupo8.sistema_de_pedidos.repository.IProductRepository;

@Service
public class ProductService implements IProductService {

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    IStockService stockService;

    private Stock createStock(AdminCreateProductRequestDTO productRequest) {
        Stock stock = new Stock();
        stock.setStockType(productRequest.getStockType());
        stock.setQuantity(productRequest.getQuantity());
        stockService.createStock(stock);
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
                    attribute.setValue(attributeDTO.getValue());
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

        if (productDTO.getName() != null) {
            product.setName(productDTO.getName());
        }

        product.getAttributes().clear();

        for (AttributeDTO attributeDTO : productDTO.getAttributes()) {
            Attribute attribute = new Attribute();
            attribute.setDescription(attributeDTO.getDescription());
            attribute.setValue(attributeDTO.getValue());
            attribute.setProduct(product);
            product.getAttributes().add(attribute);
        }

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
                        product.getProductId(),
                        product.getName(),
                        product.getAttributes().stream()
                                .map(attribute -> new AttributeDTO(attribute.getDescription(), attribute.getValue()))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }

    public Optional<Stock> getProductStock(ProductRequestDTO productDTO){

        Product product = productRepository.findById(productDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return stockService.getStockFrom(product.getProductId());
    }

    public MessageResponseDTO editStock(StockDTO stockDTO){

        stockService.editStockFrom(stockDTO);

        return new MessageResponseDTO("Stock editado correctamente");
    }
}