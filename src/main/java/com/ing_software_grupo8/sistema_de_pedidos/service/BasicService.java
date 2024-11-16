package com.ing_software_grupo8.sistema_de_pedidos.service;

import java.util.Base64;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class BasicService implements IBasicService {

    private String[] getValues(String authHeader) {
        String base64Credentials = authHeader.substring(6);
        String credentials = new String(Base64.getDecoder().decode(base64Credentials));
        return credentials.split(":");
    }

    @Override
    public String getEmailFromToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Basic "))
            return null;
        return getValues(authHeader)[0];
    }

    @Override
    public String getPasswordFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Basic "))
            return null;
        return getValues(authHeader)[1];
    }
}
