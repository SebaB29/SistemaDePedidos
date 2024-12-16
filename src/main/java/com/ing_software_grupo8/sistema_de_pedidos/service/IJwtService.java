package com.ing_software_grupo8.sistema_de_pedidos.service;

import java.util.function.Function;

import com.ing_software_grupo8.sistema_de_pedidos.entity.User;

import com.ing_software_grupo8.sistema_de_pedidos.utils.RoleEnum;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

public interface IJwtService {

    // Métodos de creación de tokens
    String createAccessToken(User user);

    String createRefreshToken(User user);

    // Métodos para obtener información del token
    String getEmailFromToken(String token);

    <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver);

    // Métodos de validación del token
    boolean isTokenExpired(String token);

    boolean isSameUser(User user, String token);

    boolean tokenHasRole(HttpServletRequest request, RoleEnum roleEnum);

    // Métodos auxiliares
    String getTokenFromRequest(HttpServletRequest request);
}
