package com.ing_software_grupo8.sistema_de_pedidos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Builder
@Data
public class GenericResponse<T> {

    @JsonProperty(required = true)
    String status;

    @JsonProperty
    String error;

    @JsonProperty
    T data;

    public static class GenericResponseBuilder<T> {
        public GenericResponseBuilder<T> status(HttpStatus httpStatus) {
            this.status = httpStatus.name();
            return this;
        }
    }
}