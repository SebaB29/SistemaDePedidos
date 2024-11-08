package com.ing_software_grupo8.sistema_de_pedidos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ing_software_grupo8.sistema_de_pedidos.DTO.AuthResponseDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.LoginRequestDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.RegisterRequestDTO;
import com.ing_software_grupo8.sistema_de_pedidos.response.GenericResponse;
import com.ing_software_grupo8.sistema_de_pedidos.service.IAuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;

    @PostMapping(value = "login")
    public ResponseEntity<GenericResponse<AuthResponseDTO>> login(@RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping(value = "register")
    public ResponseEntity<GenericResponse<Void>> register(@RequestBody RegisterRequestDTO request) {
        return ResponseEntity.ok(authService.register(request));
    }
}