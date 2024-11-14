package com.ing_software_grupo8.sistema_de_pedidos.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.ing_software_grupo8.sistema_de_pedidos.DTO.AuthResponseDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.TokenRequestDTO;
import com.ing_software_grupo8.sistema_de_pedidos.entity.User;
import com.ing_software_grupo8.sistema_de_pedidos.exception.ApiException;
import com.ing_software_grupo8.sistema_de_pedidos.repository.IUserRepository;
import com.ing_software_grupo8.sistema_de_pedidos.response.GenericResponse;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class TokenService implements ITokenService {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IJwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public GenericResponse<AuthResponseDTO> refreshToken(TokenRequestDTO tokenRequestDTO, HttpServletRequest request) {
        try {

            Optional<User> user = userRepository.findUserByEmail(tokenRequestDTO.getEmail());
            if (!user.isPresent())
                throw new ApiException(HttpStatus.UNAUTHORIZED, "Credenciales no validas.");
            User userFound = user.get();
            final String token = jwtService.getTokenFromRequest(request);
            if (!token.equals(userFound.getRefreshToken()))
                throw new ApiException(HttpStatus.UNAUTHORIZED, "Token invalido.");
            String accessToken = jwtService.createAccessToken(userFound);
            String refreshToken = jwtService.createRefreshToken(userFound);
            userFound.setRefreshToken(refreshToken);
            try {
                userRepository.save(userFound);
            } catch (DataAccessException e) {
                throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "El Usuario no se pudo guardar.");
            }
            AuthResponseDTO authResponse = AuthResponseDTO.builder().accessToken(accessToken).refreshToken(refreshToken)
                    .build();
            return GenericResponse.<AuthResponseDTO>builder()
                    .status(HttpStatus.OK)
                    .data(authResponse)
                    .build();
        } catch (

        AuthenticationException e) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "Credenciales no validas.");
        }

    }
}
