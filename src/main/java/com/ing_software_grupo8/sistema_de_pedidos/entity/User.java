package com.ing_software_grupo8.sistema_de_pedidos.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {
    @Id
    private long id;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private Number age;
    private String photo;
    private String gender;
    private String address;

    public User() {}
    public User(String name, String lastName, String email, String password, Number age, String photo, String gender, String address) {}
}
