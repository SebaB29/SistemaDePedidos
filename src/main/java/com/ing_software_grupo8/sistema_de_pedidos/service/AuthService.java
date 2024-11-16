package com.ing_software_grupo8.sistema_de_pedidos.service;

import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ing_software_grupo8.sistema_de_pedidos.DTO.AuthResponseDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.MessageResponseDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.RegisterRequestDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.RestorePasswordRequestDTO;
import com.ing_software_grupo8.sistema_de_pedidos.entity.User;
import com.ing_software_grupo8.sistema_de_pedidos.exception.ApiException;
import com.ing_software_grupo8.sistema_de_pedidos.repository.IUserRepository;
import com.ing_software_grupo8.sistema_de_pedidos.response.GenericResponse;
import com.ing_software_grupo8.sistema_de_pedidos.role.Role;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final IBasicService basicService;
    private final IJwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final IUserRepository userRepository;

    public GenericResponse<AuthResponseDTO> login(HttpServletRequest request) {
        try {
            String email = basicService.getEmailFromToken(request);
            String password = basicService.getPasswordFromRequest(request);
            Optional<User> user = userRepository.findUserByEmail(email);
            if (!user.isPresent())
                throw new ApiException(HttpStatus.NOT_FOUND, "El usuario no esta registrado");
            User userFound = user.get();
            if (!passwordEncoder.matches(password, userFound.getPassword()))
                throw new ApiException(HttpStatus.UNAUTHORIZED, "Credenciales no validas");
            String accessToken = jwtService.createAccessToken(userFound);
            String refreshToken = jwtService.createRefreshToken(userFound);
            userFound.setRefreshToken(refreshToken);
            try {
                userRepository.save(userFound);
            } catch (DataAccessException e) {
                throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Usuario no se pudo guardar.");
            }
            AuthResponseDTO authResponse = AuthResponseDTO.builder().accessToken(accessToken).refreshToken(refreshToken)
                    .build();
            return GenericResponse.<AuthResponseDTO>builder()
                    .status(HttpStatus.OK)
                    .data(authResponse)
                    .build();
        } catch (AuthenticationException e) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "Credenciales no validas.");
        }
    }

    public GenericResponse<MessageResponseDTO> register(RegisterRequestDTO request) {
        Optional<User> userFound = userRepository.findUserByEmail(request.getEmail());
        if (userFound.isPresent()) {
            throw new ApiException(HttpStatus.CONFLICT, "Usuario con ese email ya existe.");
        }
        User user = User.builder()
                .username(request.getUsername())
                .lastName(request.getLastName())
                .photo(request.getPhoto())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .age(request.getAge())
                .gender(request.getGender())
                .address(request.getAddress())
                .role(Role.USER)
                .build();

        try {
            userRepository.save(user);
        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Usuario no se pudo guardar.");
        }
        return GenericResponse.<MessageResponseDTO>builder()
                .status(HttpStatus.OK)
                .data(new MessageResponseDTO("Registrado correctamente"))
                .build();
    }

    public GenericResponse<MessageResponseDTO> restore(RestorePasswordRequestDTO request) {

        User user = findUserByEmail(request.getEmail())
                .orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "Usuario no encontrado"));

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);
        return GenericResponse.<MessageResponseDTO>builder()
                .status(HttpStatus.OK)
                .data(new MessageResponseDTO("Registrado correctamente"))
                .build();
    }

    @Transactional
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }
}