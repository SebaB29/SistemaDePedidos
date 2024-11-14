package com.ing_software_grupo8.sistema_de_pedidos.service;

import com.ing_software_grupo8.sistema_de_pedidos.DTO.AuthResponseDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.TokenRequestDTO;
import com.ing_software_grupo8.sistema_de_pedidos.response.GenericResponse;

import jakarta.servlet.http.HttpServletRequest;

public interface ITokenService {
    GenericResponse<AuthResponseDTO> refreshToken(TokenRequestDTO tokenRequestDTO, HttpServletRequest request);
}
