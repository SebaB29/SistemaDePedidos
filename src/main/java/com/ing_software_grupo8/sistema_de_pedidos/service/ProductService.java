package com.ing_software_grupo8.sistema_de_pedidos.service;

import com.ing_software_grupo8.sistema_de_pedidos.DTO.AdminCreateProductRequestDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.MessageResponseDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.ProductRequestDTO;
import com.ing_software_grupo8.sistema_de_pedidos.entity.Attribute;
import com.ing_software_grupo8.sistema_de_pedidos.entity.Product;
import com.ing_software_grupo8.sistema_de_pedidos.entity.Stock;
import com.ing_software_grupo8.sistema_de_pedidos.repository.IProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService implements IProductService {

    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private IStockService stockService;
    @Autowired
    private IAttributeService attributeService;

    private Product createProductInRepository(AdminCreateProductRequestDTO productRequest) {
        Product product = new Product();
        product.setName(productRequest.getProductName());
        product.setWeight(productRequest.getWeight());
        return productRepository.save(product);
    }

    private void createStock(long productId, AdminCreateProductRequestDTO productRequest) {
        Stock stock = new Stock();
        stock.setStockId(productId);
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

    public MessageResponseDTO createProduct(AdminCreateProductRequestDTO productRequest) {
        // if (!user.isAdmin())
        // throw new IllegalArgumentException("Only admins can create products");
        Product productSaved = createProductInRepository(productRequest);
        long productSavedId = productSaved.getProductId();
        createStock(productSavedId, productRequest);
        createAttribute(productSavedId, productRequest);
        return new MessageResponseDTO("Producto creado");
    }

    public MessageResponseDTO editProduct(ProductRequestDTO productDTO) {

        Product product = productRepository.findById(productDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        product.setName(productDTO.getName());
        product.setWeight(productDTO.getWeight());

        List<Attribute> attributes = productDTO.getAttributes().stream()
                .map(attributeDTO -> {
                    Attribute attribute = new Attribute();
                    attribute.setDescription(attributeDTO.getDescription());
                    attribute.setProductId(product.getProductId());
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
}
