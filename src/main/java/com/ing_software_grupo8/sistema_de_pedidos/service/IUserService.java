package com.ing_software_grupo8.sistema_de_pedidos.service;

import com.ing_software_grupo8.sistema_de_pedidos.DTO.MessageResponseDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.UserRequestDTO;

public interface IUserService {

    MessageResponseDTO createUser(UserRequestDTO userRequestDTO);
}
