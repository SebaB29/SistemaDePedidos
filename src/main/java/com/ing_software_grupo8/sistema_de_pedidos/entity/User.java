package com.ing_software_grupo8.sistema_de_pedidos.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private long id;

    private String name;

    private String lastName;

    private String email;

    private String password;

    private Number age;

    private String photo;

    private String gender;

    private String address;
}