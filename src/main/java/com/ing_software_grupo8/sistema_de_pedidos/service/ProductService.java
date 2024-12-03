package com.ing_software_grupo8.sistema_de_pedidos.service;

import com.ing_software_grupo8.sistema_de_pedidos.DTO.AdminCreateProductRequestDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.AttributeDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.MessageResponseDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.ProductRequestDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.ProductResponseDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.StockDTO;
import com.ing_software_grupo8.sistema_de_pedidos.entity.Product;
import com.ing_software_grupo8.sistema_de_pedidos.entity.Attribute;
import com.ing_software_grupo8.sistema_de_pedidos.entity.Stock;
import com.ing_software_grupo8.sistema_de_pedidos.repository.IProductRepository;
import com.ing_software_grupo8.sistema_de_pedidos.repository.IProductOrderRepository;
import com.ing_software_grupo8.sistema_de_pedidos.utils.RoleEnum;
import com.ing_software_grupo8.sistema_de_pedidos.exception.ApiException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService implements IProductService {

    @Autowired
    IProductRepository productRepository;

    @Autowired
    IProductOrderRepository productOrderRepository;

    @Autowired
    IStockService stockService;

    @Autowired
    IJwtService jwtService;

    @Override
    public MessageResponseDTO createProduct(AdminCreateProductRequestDTO productRequest, HttpServletRequest request) {
        verifyAdminRole(request);
        validateProductNameUniqueness(productRequest.getProductName());

        Stock stock = createStock(productRequest);
        Product product = new Product();
        product.setName(productRequest.getProductName());
        product.setPrice(productRequest.getPrice());
        product.setStock(stock);
        product.setAttributes(mapAttributes(productRequest.getAttributes(), product));

        productRepository.save(product);
        return new MessageResponseDTO("Producto creado");
    }

    @Override
    public MessageResponseDTO editProduct(ProductRequestDTO productDTO, HttpServletRequest request) {
        verifyAdminRole(request);

        Product product = findProductById(productDTO.getProductId());
        if (productDTO.getName() != null) {
            product.setName(productDTO.getName());
        }

        product.setPrice(productDTO.getPrice());

        product.getAttributes().clear();
        product.getAttributes().addAll(mapAttributes(productDTO.getAttributes(), product));
        productRepository.save(product);
        return new MessageResponseDTO("Producto editado correctamente");
    }

    @Override
    public MessageResponseDTO deleteProduct(Long productId, HttpServletRequest request) {
        verifyAdminRole(request);
        findProductById(productId);

        if (!productOrderRepository.findByProduct_ProductId(productId).isEmpty()) {
            throw new ApiException(HttpStatus.CONFLICT,
                    "El producto no puede ser eliminado, ya que pertenece a una orden");
        }

        productRepository.deleteById(productId);
        return new MessageResponseDTO("Producto eliminado correctamente");
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(product -> new ProductResponseDTO(
                        product.getProductId(),
                        product.getName(),
                        product.getStock().getQuantity(),
                        product.getPrice(),
                        product.getAttributes().stream()
                                .map(attribute -> new AttributeDTO(attribute.getDescription(), attribute.getValue()))
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    @Override
    public MessageResponseDTO editStock(StockDTO stockDTO, HttpServletRequest request) {
        verifyAdminRole(request);
        stockService.editStockFrom(stockDTO);
        return new MessageResponseDTO("Stock editado correctamente");
    }

    @Override
    public Optional<Stock> getProductStock(Long productId) {
        Product product = findProductById(productId);
        return stockService.getStockFrom(product.getProductId());
    }

    private Stock createStock(AdminCreateProductRequestDTO productRequest) {
        Stock stock = new Stock();
        stock.setStockType(productRequest.getStockType());
        stock.setQuantity(productRequest.getQuantity());
        stockService.createStock(stock);
        return stock;
    }

    private Product findProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "Producto no encontrado"));
    }

    private void verifyAdminRole(HttpServletRequest request) {
        if (!jwtService.tokenHasRole(request, RoleEnum.ADMIN)) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "No tienes autorización");
        }
    }

    private void validateProductNameUniqueness(String productName) {
        if (!productRepository.findAllByName(productName).isEmpty()) {
            throw new ApiException(HttpStatus.CONFLICT, "Ya existe este producto");
        }
    }

    private List<Attribute> mapAttributes(List<AttributeDTO> attributeDTOs, Product product) {
        return attributeDTOs.stream()
                .map(attributeDTO -> {
                    Attribute attribute = new Attribute();
                    attribute.setDescription(attributeDTO.getDescription());
                    attribute.setValue(attributeDTO.getValue());
                    attribute.setProduct(product);
                    return attribute;
                })
                .collect(Collectors.toList());
    }
}