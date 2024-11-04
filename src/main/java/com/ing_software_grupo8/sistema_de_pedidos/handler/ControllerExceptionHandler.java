package com.ing_software_grupo8.sistema_de_pedidos.handler;

import com.ing_software_grupo8.sistema_de_pedidos.exception.ApiException;
import com.ing_software_grupo8.sistema_de_pedidos.response.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<GenericResponse> handleException(NoHandlerFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(GenericResponse.builder()
                                                  .message(e.getMessage())
                                                  .status(HttpStatus.NOT_FOUND)
                                                  .build());
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<GenericResponse> handleApiException(ApiException e) {
        return ResponseEntity.status(e.getStatusCode())
                             .body(GenericResponse.builder()
                                                    .message(e.getMessage())
                                                    .status(e.getCode())
                                                    .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericResponse> handleException() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body(GenericResponse.builder()
                                                  .message("Internal server error")
                                                  .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                                  .build());
    }
}