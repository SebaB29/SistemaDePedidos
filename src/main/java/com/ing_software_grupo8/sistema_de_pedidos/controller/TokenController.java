package com.ing_software_grupo8.sistema_de_pedidos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ing_software_grupo8.sistema_de_pedidos.DTO.AuthResponseDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.TokenRequestDTO;
import com.ing_software_grupo8.sistema_de_pedidos.response.GenericResponse;
import com.ing_software_grupo8.sistema_de_pedidos.service.ITokenService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class TokenController {

    @Autowired
    ITokenService tokenService;

    @PostMapping(value = "refresh")
    public ResponseEntity<GenericResponse<AuthResponseDTO>> refreshToken(@RequestBody TokenRequestDTO tokenRequestDTO,
            HttpServletRequest request) {
        return ResponseEntity.ok(tokenService.refreshToken(tokenRequestDTO, request));
    }
}
