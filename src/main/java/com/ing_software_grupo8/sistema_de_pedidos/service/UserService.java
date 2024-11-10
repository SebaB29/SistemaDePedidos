package com.ing_software_grupo8.sistema_de_pedidos.service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.MessageResponseDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.UserRequestDTO;
import com.ing_software_grupo8.sistema_de_pedidos.entity.User;
import com.ing_software_grupo8.sistema_de_pedidos.exception.ApiException;
import com.ing_software_grupo8.sistema_de_pedidos.repository.IUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

import java.util.Map;
import java.util.Objects;

@Service
public class UserService implements IUserService {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public MessageResponseDTO createUser(UserRequestDTO userRequestDTO) {
        validateUser(userRequestDTO);

        User user = User.builder()
                .username(userRequestDTO.getUserName())
                .lastName(userRequestDTO.getLastName())
                .email(userRequestDTO.getEmail())
                .password(userRequestDTO.getPassword())
                .age(userRequestDTO.getAge())
                .photo(userRequestDTO.getPhoto())
                .gender(userRequestDTO.getGender())
                .address(userRequestDTO.getAddress())
                .build();
        userRepository.save(user);

        return new MessageResponseDTO("El usuario se creo correctamente");
    }

    private void validateUser(UserRequestDTO userRequestDTO) {
        if (userRequestDTO.getUserName().isEmpty())
            throw new IllegalArgumentException();
        if(Objects.equals(userRequestDTO.getUserName(), "")) throw new IllegalArgumentException();
    }

    @Override
    public MessageResponseDTO editUser(UserRequestDTO userRequestDTO) throws JsonMappingException {
        User user = findUserByEmail(userRequestDTO.getEmail())
                .orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "Usuario no encontrado"));

        Map<String, Object> userUpdates = objectMapper.convertValue(userRequestDTO, Map.class);

        objectMapper.updateValue(user, userUpdates);

        userRepository.save(user);

        return new MessageResponseDTO("Producto editado correctamente");
    }

    @Transactional
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

}
