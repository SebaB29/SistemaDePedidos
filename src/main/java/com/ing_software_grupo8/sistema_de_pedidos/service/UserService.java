package com.ing_software_grupo8.sistema_de_pedidos.service;

import com.ing_software_grupo8.sistema_de_pedidos.DTO.MessageResponseDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.UserRequestDTO;
import com.ing_software_grupo8.sistema_de_pedidos.entity.User;
import com.ing_software_grupo8.sistema_de_pedidos.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    IUserRepository userRepository;

    @Override
    public MessageResponseDTO createUser(UserRequestDTO userRequestDTO) {
        validateUser(userRequestDTO);

        User user = new User(userRequestDTO);
        userRepository.save(user);

        return new MessageResponseDTO("El usuario se creo correctamente");
    }

    private void validateUser(UserRequestDTO userRequestDTO) {
        if(userRequestDTO.getNombre() == "") throw new IllegalArgumentException();
    }
}
