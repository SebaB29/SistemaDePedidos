package com.ing_software_grupo8.sistema_de_pedidos.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.MessageResponseDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.UserRequestDTO;
import com.ing_software_grupo8.sistema_de_pedidos.entity.User;
import com.ing_software_grupo8.sistema_de_pedidos.exception.ApiException;
import com.ing_software_grupo8.sistema_de_pedidos.repository.IUserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
public class UserService implements IUserService {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    IJwtService jwtService;    

    @Override
    public MessageResponseDTO createUser(UserRequestDTO userRequestDTO, HttpServletRequest request) {
        validateUser(userRequestDTO, request);

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

    private void validateUser(UserRequestDTO userRequestDTO, HttpServletRequest request) {
        if (!jwtService.tokenHasRoleAdmin(request))
            throw new ApiException(HttpStatus.UNAUTHORIZED, "No tienes autorizacion");
        if (userRequestDTO.getUserName().isEmpty())
            throw new IllegalArgumentException();
        if (Objects.equals(userRequestDTO.getUserName(), ""))
            throw new IllegalArgumentException();
    }

    @Override
    public MessageResponseDTO editUser(UserRequestDTO userRequestDTO, HttpServletRequest request)
            throws JsonMappingException {
                
        User user = findUserByEmail(userRequestDTO.getEmail())
            .orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "Usuario no encontrado"));
        if (!jwtService.isSameUser(user, jwtService.getTokenFromRequest(request)))
            throw new ApiException(HttpStatus.UNAUTHORIZED, "No tienes autorizacion");
            
        user.setUsername(userRequestDTO.getUserName());
        user.setLastName(userRequestDTO.getLastName());
        user.setEmail(userRequestDTO.getEmail());
        user.setAge(userRequestDTO.getAge());
        user.setPhoto(userRequestDTO.getPhoto());
        user.setGender(userRequestDTO.getGender());
        user.setAddress(userRequestDTO.getAddress());

        userRepository.save(user);

        return new MessageResponseDTO("Usuario editado correctamente");
    }

    @Transactional
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public Optional<User> getUser(String userId){
        User user = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "Usuario no encontrado"));


        return Optional.ofNullable(user);
    }

}
