package com.ing_software_grupo8.sistema_de_pedidos.service;

import com.ing_software_grupo8.sistema_de_pedidos.DTO.MessageResponseDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.UserRequestDTO;
import com.ing_software_grupo8.sistema_de_pedidos.entity.User;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public interface IUserService {

    MessageResponseDTO editUser(UserRequestDTO userRequestDTO, HttpServletRequest request);

    Optional<User> getUser(String userEmail);
}
