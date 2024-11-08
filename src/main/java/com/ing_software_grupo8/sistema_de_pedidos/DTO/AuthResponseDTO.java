package com.ing_software_grupo8.sistema_de_pedidos.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {

    @JsonProperty(value = "access_token")
    String accessToken;

    @JsonProperty(value = "refresh_token")
    String refreshToken;
}
