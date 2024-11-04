package com.ing_software_grupo8.sistema_de_pedidos.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException{

    private final String code;

    private final HttpStatus statusCode;

    private final String message;

    public ApiException(String code, HttpStatus statusCode, String message) {
        super(message);
        this.code = code;
        this.statusCode = statusCode;
        this.message = message;
    }
}
