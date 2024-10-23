package com.ing_software_grupo8.sistema_de_pedidos.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.util.GenericSignature;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

    @JsonProperty(defaultValue = "")
    public String nombre;

    public String apellido;

    public String email;

    public String password;

    public String age;

    public String photo;

    public String gender;

    public String address;
}
