package com.ing_software_grupo8.sistema_de_pedidos.service;

import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;

public interface IJwtService {

    String createAccessToken(UserDetails user);

    String createRefreshToken(UserDetails user);

    String getUsernameFromToken(String token);

    boolean isTokenValid(String token, UserDetails userDetails);

    <T> T getClaim(String token, Function<Claims, T> claimsResolver);
}