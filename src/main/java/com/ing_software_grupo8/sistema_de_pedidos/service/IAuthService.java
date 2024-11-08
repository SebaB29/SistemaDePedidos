package com.ing_software_grupo8.sistema_de_pedidos.service;

import com.ing_software_grupo8.sistema_de_pedidos.DTO.AuthResponseDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.LoginRequestDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.RegisterRequestDTO;
import com.ing_software_grupo8.sistema_de_pedidos.response.GenericResponse;

public interface IAuthService {

    GenericResponse<Void> register(RegisterRequestDTO request);

    GenericResponse<AuthResponseDTO> login(LoginRequestDTO request);

}
