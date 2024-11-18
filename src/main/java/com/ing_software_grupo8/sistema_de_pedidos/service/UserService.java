package com.ing_software_grupo8.sistema_de_pedidos.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
    IJwtService jwtService;

    @Override
    public MessageResponseDTO createUser(UserRequestDTO userRequestDTO, HttpServletRequest request) {
        verifyAdminRole(request);
        validateUserRequest(userRequestDTO);

        User user = mapToUser(userRequestDTO);
        userRepository.save(user);

        return new MessageResponseDTO("El usuario se creó correctamente");
    }

    @Override
    public MessageResponseDTO editUser(UserRequestDTO userRequestDTO, HttpServletRequest request) {
        User user = findUserByEmailOrThrow(userRequestDTO.getEmail());
        verifySameUser(user, request);

        updateUser(user, userRequestDTO);
        userRepository.save(user);

        return new MessageResponseDTO("Usuario editado correctamente");
    }

    @Transactional
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public Optional<User> getUser(String email) {
        return userRepository.findUserByEmail(email);
    }

    private void verifyAdminRole(HttpServletRequest request) {
        if (!jwtService.tokenHasRoleAdmin(request)) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "No tienes autorización");
        }
    }

    private void verifySameUser(User user, HttpServletRequest request) {
        if (!jwtService.isSameUser(user, jwtService.getTokenFromRequest(request))) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "No tienes autorización");
        }
    }

    private void validateUserRequest(UserRequestDTO userRequestDTO) {
        if (userRequestDTO.getUserName() == null || userRequestDTO.getUserName().isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario no puede estar vacío");
        }
    }

    private User mapToUser(UserRequestDTO userRequestDTO) {
        return User.builder()
                .username(userRequestDTO.getUserName())
                .lastName(userRequestDTO.getLastName())
                .email(userRequestDTO.getEmail())
                .password(userRequestDTO.getPassword())
                .age(userRequestDTO.getAge())
                .photo(userRequestDTO.getPhoto())
                .gender(userRequestDTO.getGender())
                .address(userRequestDTO.getAddress())
                .build();
    }

    private void updateUser(User user, UserRequestDTO userRequestDTO) {
        user.setUsername(userRequestDTO.getUserName());
        user.setLastName(userRequestDTO.getLastName());
        user.setEmail(userRequestDTO.getEmail());
        user.setAge(userRequestDTO.getAge());
        user.setPhoto(userRequestDTO.getPhoto());
        user.setGender(userRequestDTO.getGender());
        user.setAddress(userRequestDTO.getAddress());
    }

    private User findUserByEmailOrThrow(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "Usuario no encontrado"));
    }

    private User findUserByIdOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "Usuario no encontrado"));
    }
}
