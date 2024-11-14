package com.ing_software_grupo8.sistema_de_pedidos.service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.MessageResponseDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.UserRequestDTO;
import com.ing_software_grupo8.sistema_de_pedidos.entity.User;

import java.util.Optional;

public interface IUserService {

    MessageResponseDTO createUser(UserRequestDTO userRequestDTO);

    MessageResponseDTO editUser(UserRequestDTO userRequestDTO) throws JsonMappingException;

    Optional<User> getUser(String userEmail);
}
