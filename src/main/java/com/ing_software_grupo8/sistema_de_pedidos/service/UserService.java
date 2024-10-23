package com.ing_software_grupo8.sistema_de_pedidos.service;

import com.ing_software_grupo8.sistema_de_pedidos.DTO.ResponseDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.UserDTO;
import com.ing_software_grupo8.sistema_de_pedidos.entity.User;
import com.ing_software_grupo8.sistema_de_pedidos.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    IUserRepository userRepository;

    @Override
    public ResponseDTO CreateUser(UserDTO userDTO) {
        User user = new User(); //TODO: lo cree a modo de ejemplo, hay que borrarlo
        userRepository.save(user);

        return new ResponseDTO("User creado con exito!!1111");
    }
}
