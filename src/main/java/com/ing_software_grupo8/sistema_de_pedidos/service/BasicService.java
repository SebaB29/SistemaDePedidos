package com.ing_software_grupo8.sistema_de_pedidos.service;

import java.util.Base64;

import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class BasicService implements IBasicService {

    private static final String BASIC_PREFIX = "Basic ";

    private String[] extractCredentials(String authHeader) {
        if (authHeader == null || !authHeader.startsWith(BASIC_PREFIX)) {
            return null;
        }

        String base64Credentials = authHeader.substring(BASIC_PREFIX.length());
        String credentials = new String(Base64.getDecoder().decode(base64Credentials));
        String[] values = credentials.split(":");

        if (values.length != 2) {
            throw new IllegalArgumentException("Invalid authorization header format");
        }

        return values;
    }

    // Métodos públicos
    @Override
    public String getEmailFromToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String[] credentials = extractCredentials(authHeader);

        return credentials != null ? credentials[0] : null;
    }

    @Override
    public String getPasswordFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String[] credentials = extractCredentials(authHeader);

        return credentials != null ? credentials[1] : null;
    }
}
