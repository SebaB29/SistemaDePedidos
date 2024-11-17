package com.ing_software_grupo8.sistema_de_pedidos.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ing_software_grupo8.sistema_de_pedidos.exception.ApiException;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FilterException {

    private final ObjectMapper objectMapper;

    public void write(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        Map<String, Object> responseMap = new HashMap<>();
        ApiException apiException = new ApiException(status, message);
        responseMap.put("statusCode", apiException.getStatusCode().value());
        responseMap.put("message", apiException.getMessage());
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(responseMap));
    }
}
