package com.ing_software_grupo8.sistema_de_pedidos.service;

import com.ing_software_grupo8.sistema_de_pedidos.DTO.*;
import com.ing_software_grupo8.sistema_de_pedidos.entity.*;
import com.ing_software_grupo8.sistema_de_pedidos.exception.ApiException;
import com.ing_software_grupo8.sistema_de_pedidos.repository.IOrderRepository;
import com.ing_software_grupo8.sistema_de_pedidos.repository.IOrderStateRepository;
import com.ing_software_grupo8.sistema_de_pedidos.repository.IProductRepository;
import com.ing_software_grupo8.sistema_de_pedidos.repository.IUserRepository;
import com.ing_software_grupo8.sistema_de_pedidos.utils.OrderStateEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private IOrderRepository orderRepository;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IProductRepository productRepository;

    @Mock
    private IOrderStateRepository orderStateRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUserNotFoundShouldThrowApiException() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        ApiException exception = assertThrows(ApiException.class, () -> orderService.getAll(userId));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Usuario no encontrado.", exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void getAllValidUserWithOrdersShouldReturnOrderList() {
        Long userId = 1L;
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        List<Order> orders = new ArrayList<>();
        Order order = new Order();
        OrderState orderState = new OrderState();
        orderState.setStateCode(OrderStateEnum.CREADO.ordinal());
        orderState.setStateDesc("Creado");
        order.setOrderState(orderState);
        order.setOrderDate(LocalTime.now());

        List<ProductOrder> productOrders = new ArrayList<>();
        Product product = new Product();
        product.setName("Producto de prueba");
        product.setStock(new Stock(1L,"KG",10));
        Attribute attribute = new Attribute(1L, product, "Descripción", "Valor");
        product.setAttributes(Arrays.asList(attribute));
        ProductOrder productOrder = new ProductOrder();
        productOrder.setOrderQuantity(2);
        productOrder.setProduct(product);
        productOrders.add(productOrder);

        order.setProductOrder(productOrders);
        orders.add(order);
        when(orderRepository.findAllByUser(user)).thenReturn(orders);

        OrderListDTO result = orderService.getAll(userId);

        assertNotNull(result);
        assertFalse(result.getOrderResponseDTOList().isEmpty());
        assertEquals(1, result.getOrderResponseDTOList().size());
        assertEquals("Creado", result.getOrderResponseDTOList().get(0).getOrderState());
    }

    @Test
    void createInvalidUserShouldThrowApiException() {
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
        orderRequestDTO.setUserId(1L);
        when(userRepository.existsById(orderRequestDTO.getUserId())).thenReturn(false);

        ApiException exception = assertThrows(ApiException.class, () -> orderService.create(orderRequestDTO));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Usuario no encontrado.", exception.getMessage());
        verify(userRepository, times(1)).existsById(orderRequestDTO.getUserId());
    }

    @Test
    void createInvalidProductShouldThrowApiException() {

        ProductOrderDTO productOrderDTO = new ProductOrderDTO(2L,1);
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO(1L,  List.of(productOrderDTO));

        when(userRepository.existsById(orderRequestDTO.getUserId())).thenReturn(true);
        when(productRepository.existsById(productOrderDTO.getProductId())).thenReturn(false);

        ApiException exception = assertThrows(ApiException.class, () -> orderService.create(orderRequestDTO));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Product no encontrado.", exception.getMessage());
        verify(userRepository, times(1)).existsById(orderRequestDTO.getUserId());
        verify(productRepository, times(1)).existsById(productOrderDTO.getProductId());
    }

    @Test
    void createValidOrderShouldSaveOrder() {
        ProductOrderDTO productOrderDTO = new ProductOrderDTO(2L,1);
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO(1L,List.of(productOrderDTO));

        User user = new User();
        when(userRepository.existsById(orderRequestDTO.getUserId())).thenReturn(true);
        when(userRepository.findById(orderRequestDTO.getUserId())).thenReturn(Optional.of(user));

        Product product = new Product();
        product.setStock(new Stock(1L,"KG",10));
        when(productRepository.existsById(productOrderDTO.getProductId())).thenReturn(true);
        when(productRepository.findById(productOrderDTO.getProductId())).thenReturn(Optional.of(product));

        when(orderStateRepository.findByStateCode(OrderStateEnum.CREADO.ordinal())).thenReturn(new OrderState(1L, OrderStateEnum.CREADO.ordinal(), "Creado"));

        MessageResponseDTO result = orderService.create(orderRequestDTO);

        assertNotNull(result);
        assertEquals("Orden creada correctamente", result.getMessage());
        verify(orderRepository, times(1)).save(any(Order.class));
    }
}

