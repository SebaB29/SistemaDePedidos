package com.ing_software_grupo8.sistema_de_pedidos.service;

import com.ing_software_grupo8.sistema_de_pedidos.DTO.*;
import com.ing_software_grupo8.sistema_de_pedidos.entity.Attribute;
import com.ing_software_grupo8.sistema_de_pedidos.entity.Product;
import com.ing_software_grupo8.sistema_de_pedidos.entity.Stock;
import com.ing_software_grupo8.sistema_de_pedidos.exception.ApiException;
import com.ing_software_grupo8.sistema_de_pedidos.repository.IProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductServiceTest {
    @Mock
    private IProductRepository productRepository;

    @Mock
    private IStockService stockService;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProduct() {
        AdminCreateProductRequestDTO request = new AdminCreateProductRequestDTO();
        request.setProductName("Producto Test");
        request.setQuantity(10);
        request.setStockType("Tipo de Stock");

        AttributeDTO attributeDTO = new AttributeDTO("Descripción", "Valor");
        request.setAttributes(Arrays.asList(attributeDTO));

        Stock stock = new Stock();
        doNothing().when(stockService).createStock(stock);

        MessageResponseDTO response = productService.createProduct(request);

        assertEquals("Producto creado", response.getMessage());
        verify(productRepository, times(1)).save(any(Product.class));
        verify(stockService, times(1)).createStock(any(Stock.class));
    }

    @Test
    void testEditProduct() {
        ProductRequestDTO request = new ProductRequestDTO();
        request.setProductId(1L);
        request.setName("Producto Editado");

        AttributeDTO attributeDTO = new AttributeDTO("Descripción Editada", "Valor Editado");
        request.setAttributes(Arrays.asList(attributeDTO));

        Product product = new Product();
        product.setName("Producto Original");
        Attribute originalAttribute = new Attribute();
        originalAttribute.setDescription("Descripción");
        originalAttribute.setValue("Valor");
        List<Attribute> attributes = new ArrayList<>();
        attributes.add(originalAttribute);
        product.setAttributes(attributes);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        MessageResponseDTO response = productService.editProduct(request);

        assertEquals("Producto editado correctamente", response.getMessage());
        verify(productRepository, times(1)).save(product);
        assertEquals("Producto Editado", product.getName());
    }

    @Test
    void testEditProduct_ProductNotFound() {
        ProductRequestDTO request = new ProductRequestDTO();
        request.setProductId(999L);

        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        ApiException exception = assertThrows(ApiException.class, () -> productService.editProduct(request));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Producto no encontrado", exception.getMessage());
    }

    @Test
    void testDeleteProduct() {
        ProductRequestDTO request = new ProductRequestDTO();
        request.setProductId(1L);

        Product product = new Product();
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        MessageResponseDTO response = productService.deleteProduct(request.getProductId());

        assertEquals("Producto eliminado correctamente", response.getMessage());
        verify(productRepository, times(1)).delete(product);
    }

    @Test
    void testDeleteProduct_ProductNotFound() {
        ProductRequestDTO request = new ProductRequestDTO();
        request.setProductId(999L);

        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        ApiException exception = assertThrows(ApiException.class, () -> productService.deleteProduct(request.getProductId()));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Producto no encontrado", exception.getMessage());
    }

    @Test
    void testGetAllProducts() {
        Product product = new Product();
        product.setName("Producto Test");

        Attribute attribute = new Attribute();
        attribute.setDescription("Descripción");
        attribute.setValue("Valor");
        product.setAttributes(Arrays.asList(attribute));

        Product product2 = new Product();
        product2.setName("Producto Test 2");

        Attribute attribute2 = new Attribute();
        attribute2.setDescription("Descripción 2");
        attribute2.setValue("Valor 2");
        product2.setAttributes(Arrays.asList(attribute2));

        when(productRepository.findAll()).thenReturn(Arrays.asList(product,product2));

        List<ProductResponseDTO> products = productService.getAllProducts();

        assertEquals(2, products.size());
        assertEquals("Producto Test", products.get(0).getName());
        assertEquals("Descripción", products.get(0).getAttributes().get(0).getDescription());
        assertEquals("Producto Test 2", products.get(1).getName());
        assertEquals("Descripción 2", products.get(1).getAttributes().get(0).getDescription());
    }

    @Test
    void testGetProductStock() {
        ProductRequestDTO request = new ProductRequestDTO();
        request.setProductId(1L);

        Product product = new Product();
        product.setProductId(1L);

        Stock stock = new Stock();
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(stockService.getStockFrom(1L)).thenReturn(Optional.of(stock));

        Optional<Stock> result = productService.getProductStock(request.getProductId());

        assertTrue(result.isPresent());
        assertEquals(stock, result.get());
    }

    @Test
    void testEditStock() {
        StockDTO stockDTO = new StockDTO();
        stockDTO.setQuantity(20);

        MessageResponseDTO response = productService.editStock(stockDTO);

        assertEquals("Stock editado correctamente", response.getMessage());
        verify(stockService, times(1)).editStockFrom(stockDTO);
    }
}
