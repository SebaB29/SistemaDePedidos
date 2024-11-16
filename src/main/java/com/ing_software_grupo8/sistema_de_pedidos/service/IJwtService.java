package com.ing_software_grupo8.sistema_de_pedidos.service;

import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;

import com.ing_software_grupo8.sistema_de_pedidos.entity.User;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

public interface IJwtService {

    String createAccessToken(User user);

    String createRefreshToken(User user);

    String getEmailFromToken(String token);

    boolean isTokenValid(String token, UserDetails userDetails);

    boolean isTokenExpired(String token);

    boolean isSameUser(User user, String token);

    boolean tokenHasRoleAdmin(HttpServletRequest request);

    boolean tokenHasRoleUser(HttpServletRequest request);

    String getTokenFromRequest(HttpServletRequest request);

    <T> T getClaim(String token, Function<Claims, T> claimsResolver);

}