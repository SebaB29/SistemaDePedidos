package com.ing_software_grupo8.sistema_de_pedidos.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

    @JsonProperty(defaultValue = "")
    public String userName;

    @JsonProperty(defaultValue = "")
    private String lastName;

    @JsonProperty(defaultValue = "")
    private String email;

    @JsonProperty(defaultValue = "")
    private String password;

    @JsonProperty(defaultValue = "0")
    private Long age;

    @JsonProperty(defaultValue = "")
    private String photo;

    @JsonProperty(defaultValue = "")
    private String gender;

    @JsonProperty(defaultValue = "")
    private String address;
}
