package com.ing_software_grupo8.sistema_de_pedidos.service;

import java.util.Optional;

import com.ing_software_grupo8.sistema_de_pedidos.DTO.MessageResponseDTO;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ing_software_grupo8.sistema_de_pedidos.DTO.AuthResponseDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.LoginRequestDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.RegisterRequestDTO;
import com.ing_software_grupo8.sistema_de_pedidos.entity.User;
import com.ing_software_grupo8.sistema_de_pedidos.exception.ApiException;
import com.ing_software_grupo8.sistema_de_pedidos.repository.IUserRepository;
import com.ing_software_grupo8.sistema_de_pedidos.response.GenericResponse;
import com.ing_software_grupo8.sistema_de_pedidos.role.Role;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final IJwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final IUserRepository userRepository;

    public GenericResponse<AuthResponseDTO> login(LoginRequestDTO request) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            User user = (User) authentication.getPrincipal();
            String accessToken = jwtService.createAccessToken(user);
            String refreshToken = jwtService.createRefreshToken(user);
            user.setRefreshToken(refreshToken);
            try {
                userRepository.save(user);
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
}