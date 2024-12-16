package com.ing_software_grupo8.sistema_de_pedidos.service;

import com.ing_software_grupo8.sistema_de_pedidos.DTO.MessageResponseDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.UserRequestDTO;
import com.ing_software_grupo8.sistema_de_pedidos.entity.User;
import com.ing_software_grupo8.sistema_de_pedidos.repository.IUserRepository;
import com.ing_software_grupo8.sistema_de_pedidos.exception.ApiException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IJwtService jwtService;

    @Override
    @Transactional
    public MessageResponseDTO editUser(UserRequestDTO userRequestDTO, HttpServletRequest request) {
        User user = findUserByEmail(userRequestDTO.getEmail());
        validateUserAccess(user, request);

        updateUserDetails(user, userRequestDTO);
        saveUser(user);

        return new MessageResponseDTO("Usuario editado correctamente");
    }

    @Override
    public Optional<User> getUser(String email) {
        return userRepository.findUserByEmail(email);
    }

    // Métodos auxiliares

    private User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "Usuario no encontrado"));
    }

    private void validateUserAccess(User user, HttpServletRequest request) {
        if (!jwtService.isSameUser(user, jwtService.getTokenFromRequest(request))) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "No tienes autorización");
        }
    }

    private void updateUserDetails(User user, UserRequestDTO userRequestDTO) {
        user.setUsername(userRequestDTO.getUserName());
        user.setLastName(userRequestDTO.getLastName());
        user.setEmail(userRequestDTO.getEmail());
        user.setAge(userRequestDTO.getAge());
        user.setPhoto(userRequestDTO.getPhoto());
        user.setGender(userRequestDTO.getGender());
        user.setAddress(userRequestDTO.getAddress());
    }

    private void saveUser(User user) {
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar el usuario");
        }
    }
}
