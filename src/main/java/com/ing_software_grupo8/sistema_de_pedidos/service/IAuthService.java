package com.ing_software_grupo8.sistema_de_pedidos.service;

import com.ing_software_grupo8.sistema_de_pedidos.DTO.AuthResponseDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.MessageResponseDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.RegisterRequestDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.RestorePasswordRequestDTO;
import com.ing_software_grupo8.sistema_de_pedidos.response.GenericResponse;

import jakarta.servlet.http.HttpServletRequest;

public interface IAuthService {

    GenericResponse<AuthResponseDTO> login(HttpServletRequest request);

    GenericResponse<MessageResponseDTO> register(RegisterRequestDTO request);

    GenericResponse<MessageResponseDTO> restore(RestorePasswordRequestDTO request);
}
