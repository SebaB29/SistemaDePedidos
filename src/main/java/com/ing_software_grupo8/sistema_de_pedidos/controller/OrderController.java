package com.ing_software_grupo8.sistema_de_pedidos.controller;

import com.ing_software_grupo8.sistema_de_pedidos.DTO.OrderRequestDTO;
import com.ing_software_grupo8.sistema_de_pedidos.response.GenericResponse;
import com.ing_software_grupo8.sistema_de_pedidos.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController{

    @Autowired
    private IOrderService orderService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody OrderRequestDTO orderRequestDTO){
        return ResponseEntity.ok(GenericResponse.builder()
                                                .data(orderService.create(orderRequestDTO))
                                                .status(HttpStatus.OK)
                                                .message("La orden se creo correctamente")
                                                .build());
    }
}
