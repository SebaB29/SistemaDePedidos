package com.ing_software_grupo8.sistema_de_pedidos.service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.MessageResponseDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.UserRequestDTO;
import com.ing_software_grupo8.sistema_de_pedidos.entity.User;

import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;

public interface IUserService {

    MessageResponseDTO createUser(UserRequestDTO userRequestDTO, HttpServletRequest request);

    MessageResponseDTO editUser(UserRequestDTO userRequestDTO, HttpServletRequest request) throws JsonMappingException;

    Optional<User> getUser(String userEmail);;
}
