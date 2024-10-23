package com.ing_software_grupo8.sistema_de_pedidos.service;

import com.ing_software_grupo8.sistema_de_pedidos.DTO.ResponseDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.UserDTO;

public interface IUserService {

    ResponseDTO CreateUser(UserDTO userDTO); //TODO: Agregar parametros que necesite
}
