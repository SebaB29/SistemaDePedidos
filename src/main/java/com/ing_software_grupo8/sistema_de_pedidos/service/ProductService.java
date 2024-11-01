package com.ing_software_grupo8.sistema_de_pedidos.service;

import com.ing_software_grupo8.sistema_de_pedidos.DTO.MessageResponseDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.ProductRequestDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.StockDTO;
import com.ing_software_grupo8.sistema_de_pedidos.entity.Attribute;
import com.ing_software_grupo8.sistema_de_pedidos.entity.Product;
import com.ing_software_grupo8.sistema_de_pedidos.entity.Stock;
import com.ing_software_grupo8.sistema_de_pedidos.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    IProductRepository productRepository;

    @Autowired
    IStockService stockService;


    public MessageResponseDTO editProduct(ProductRequestDTO productDTO) {

        Product product = productRepository.findById(productDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        product.setName(productDTO.getName());
        product.setWeight(productDTO.getWeight());

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
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        productRepository.delete(product);
        return new MessageResponseDTO("Producto eliminado correctamente");
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
