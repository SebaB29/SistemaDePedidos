package com.ing_software_grupo8.sistema_de_pedidos.service;

import com.ing_software_grupo8.sistema_de_pedidos.DTO.AuthResponseDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.TokenRequestDTO;
import com.ing_software_grupo8.sistema_de_pedidos.entity.User;
import com.ing_software_grupo8.sistema_de_pedidos.exception.ApiException;
import com.ing_software_grupo8.sistema_de_pedidos.repository.IUserRepository;
import com.ing_software_grupo8.sistema_de_pedidos.response.GenericResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

import java.util.Optional;

@Service
public class TokenService implements ITokenService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IJwtService jwtService;

    @Override
    @Transactional
    public GenericResponse<AuthResponseDTO> refreshToken(TokenRequestDTO tokenRequestDTO, HttpServletRequest request) {
        User user = validateUserAndToken(tokenRequestDTO, request);
        String newAccessToken = jwtService.createAccessToken(user);
        String newRefreshToken = jwtService.createRefreshToken(user);
        user.setRefreshToken(newRefreshToken);

        saveUser(user);

        AuthResponseDTO authResponse = AuthResponseDTO.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();

        return GenericResponse.<AuthResponseDTO>builder()
                .status(HttpStatus.OK)
                .data(authResponse)
                .build();
    }

    // Métodos auxiliares

    private User validateUserAndToken(TokenRequestDTO tokenRequestDTO, HttpServletRequest request) {
        Optional<User> optionalUser = userRepository.findUserByEmail(tokenRequestDTO.getEmail());
        if (optionalUser.isEmpty()) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "Credenciales no válidas.");
        }

        User user = optionalUser.get();

        String tokenFromRequest = jwtService.getTokenFromRequest(request);
        if (!tokenFromRequest.equals(user.getRefreshToken())) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "Token inválido.");
        }

        return user;
    }

    private void saveUser(User user) {
        try {
            userRepository.save(user);
        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "El Usuario no se pudo guardar.");
        }
    }
}
