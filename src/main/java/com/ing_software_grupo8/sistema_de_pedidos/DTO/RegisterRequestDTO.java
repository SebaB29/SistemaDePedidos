package com.ing_software_grupo8.sistema_de_pedidos.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {
    private String username;

    private String lastName;

    private String email;

    private String password;

    private Long age;

    private String photo;

    private String gender;

    private String address;
}