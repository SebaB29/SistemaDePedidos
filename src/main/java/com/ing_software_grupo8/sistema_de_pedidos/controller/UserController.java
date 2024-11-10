package com.ing_software_grupo8.sistema_de_pedidos.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.UserRequestDTO;
import com.ing_software_grupo8.sistema_de_pedidos.response.GenericResponse;
import com.ing_software_grupo8.sistema_de_pedidos.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @PutMapping("/user")
    public ResponseEntity<GenericResponse> editUser(@RequestBody UserRequestDTO userRequestDTO) throws JsonMappingException {
        return ResponseEntity.ok(GenericResponse.builder()
                .data(userService.editUser(userRequestDTO))
                .status(HttpStatus.OK)
                .build());
    }
}