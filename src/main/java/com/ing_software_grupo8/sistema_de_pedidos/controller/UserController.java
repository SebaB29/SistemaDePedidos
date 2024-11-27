package com.ing_software_grupo8.sistema_de_pedidos.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.UserRequestDTO;
import com.ing_software_grupo8.sistema_de_pedidos.response.GenericResponse;
import com.ing_software_grupo8.sistema_de_pedidos.service.IUserService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/{userEmail}")
    public ResponseEntity<GenericResponse> getUser(@PathVariable String userEmail) throws JsonMappingException {
        return ResponseEntity.ok(GenericResponse.builder()
                .data(userService.getUser(userEmail))
                .status(HttpStatus.OK)
                .build());
    }

    @PutMapping
    public ResponseEntity<GenericResponse> editUser(@RequestBody UserRequestDTO userRequestDTO,
            HttpServletRequest request)
            throws JsonMappingException {
        return ResponseEntity.ok(GenericResponse.builder()
                .data(userService.editUser(userRequestDTO, request))
                .status(HttpStatus.OK)
                .build());
    }
}