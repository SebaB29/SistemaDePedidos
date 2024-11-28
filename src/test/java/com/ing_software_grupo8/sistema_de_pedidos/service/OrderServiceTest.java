package com.ing_software_grupo8.sistema_de_pedidos.service;

import com.ing_software_grupo8.sistema_de_pedidos.DTO.*;
import com.ing_software_grupo8.sistema_de_pedidos.entity.*;
import com.ing_software_grupo8.sistema_de_pedidos.exception.ApiException;
import com.ing_software_grupo8.sistema_de_pedidos.repository.*;
import com.ing_software_grupo8.sistema_de_pedidos.rules.RuleManager;
import com.ing_software_grupo8.sistema_de_pedidos.utils.OrderStateEnum;
import com.ing_software_grupo8.sistema_de_pedidos.utils.RoleEnum;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Mock
    private IJwtService jwtService;

    @Mock
    private RuleManager ruleManager;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUserNotFoundShouldThrowApiException() {
        HttpServletRequest servletRequest = new MockHttpServletRequest();
        User user = new User();
        user.setUserId(1L);
        when(userRepository.findById(user.getUserId())).thenReturn(Optional.empty());
        when(jwtService.tokenHasRoleUser(servletRequest)).thenReturn(true);

        ApiException exception = assertThrows(ApiException.class, () -> orderService.getAll(user.getUserId(), servletRequest));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Usuario no encontrado.", exception.getMessage());
        verify(userRepository, times(1)).findById(user.getUserId());
    }

    @Test
    void getAllValidUserWithOrdersShouldReturnOrderList() {
        HttpServletRequest servletRequest = new MockHttpServletRequest();

        User user = new User();
        user.setUserId(1L);
        user.setRole(RoleEnum.USER);
        when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
        when(!jwtService.tokenHasRoleUser(servletRequest)).thenReturn(true);

        List<Order> orders = new ArrayList<>();
        Order order = new Order();
        OrderState orderState = new OrderState();
        orderState.setStateCode(OrderStateEnum.CONFIRMADO.ordinal());
        orderState.setStateDesc("Confirmado");
        order.setOrderState(orderState);
        order.setOrderDate(LocalDateTime.now());

        List<ProductOrder> productOrders = new ArrayList<>();
        Product product = new Product();
        product.setName("Producto de prueba");
        product.setStock(new Stock(1L, "KG", 10f));
        Attribute attribute = new Attribute(1L, product, "Descripción", "Valor");
        product.setAttributes(List.of(attribute));
        ProductOrder productOrder = new ProductOrder();
        productOrder.setOrderQuantity(2);
        productOrder.setProduct(product);
        productOrders.add(productOrder);

        order.setProductOrder(productOrders);
        orders.add(order);
        when(orderRepository.findAllByUser(user)).thenReturn(orders);

        OrderListDTO result = orderService.getAll(user.getUserId(), servletRequest);

        assertNotNull(result);
        assertFalse(result.getOrderResponseDTOList().isEmpty());
        assertEquals(1, result.getOrderResponseDTOList().size());
        assertEquals("Confirmado", result.getOrderResponseDTOList().getFirst().getOrderState());
    }

    @Test
    void createInvalidUserShouldThrowApiException() {
        HttpServletRequest servletRequest = new MockHttpServletRequest();
        ProductOrderDTO productOrderDTO = new ProductOrderDTO(2L, 1);
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO(1L, 1L, 1, List.of(productOrderDTO));
        Product product = new Product();
        product.setStock(new Stock(1L, "KG", 10f));

        when(jwtService.tokenHasRoleUser(servletRequest)).thenReturn(true);
        when(productRepository.findById(2L)).thenReturn(Optional.of(product));

        ApiException exception = assertThrows(ApiException.class, () -> orderService.create(orderRequestDTO, servletRequest));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Usuario no encontrado.", exception.getMessage());

        verify(userRepository, times(1)).findById(orderRequestDTO.getUserId());
        verify(productRepository, never()).existsById(anyLong());
    }

    @Test
    void createInvalidProductShouldThrowApiException() {
        HttpServletRequest servletRequest = new MockHttpServletRequest();
        ProductOrderDTO productOrderDTO = new ProductOrderDTO(2L, 1);
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO(1L, 1L, 1, List.of(productOrderDTO));

        when(!jwtService.tokenHasRoleUser(servletRequest)).thenReturn(true);

        ApiException exception = assertThrows(ApiException.class, () -> orderService.create(orderRequestDTO, servletRequest));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Producto no encontrado.", exception.getMessage());
    }

    @Test
    void createValidOrderShouldSaveOrder() {
        HttpServletRequest servletRequest = new MockHttpServletRequest();
        ProductOrderDTO productOrderDTO = new ProductOrderDTO(2L, 1);
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO(1L, 1L, 1, List.of(productOrderDTO));
        Product product = new Product();
        product.setProductId(2L);
        product.setStock(new Stock(1L, "KG", 10f));
        User user = new User();

        when(jwtService.tokenHasRoleUser(servletRequest)).thenReturn(true);
        when(ruleManager.validateOrder(any(Order.class))).thenReturn(true);
        when(productRepository.findById(2L)).thenReturn(Optional.of(product));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(orderStateRepository.findByStateCode(OrderStateEnum.CONFIRMADO.ordinal())).thenReturn(new OrderState(1L, 0, "Confirmado"));

        MessageResponseDTO result = orderService.create(orderRequestDTO, servletRequest);

        assertNotNull(result);
        assertEquals("Orden creada correctamente", result.getMessage());
        verify(orderRepository, times(1)).save(any(Order.class));
    }
}
