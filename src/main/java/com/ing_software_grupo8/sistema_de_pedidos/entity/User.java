package com.ing_software_grupo8.sistema_de_pedidos.entity;

import com.ing_software_grupo8.sistema_de_pedidos.DTO.UserRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Number age;

    @Column(nullable = false)
    private String photo;

    @Column(nullable = false)
    private String gender;

    @Column
    private String address;

    public User(UserRequestDTO userRequestDTO) {
        this.name = userRequestDTO.getNombre();
        this.lastName = userRequestDTO.getApellido();
        this.email = userRequestDTO.getEmail();
        this.password = userRequestDTO.getPassword();
        this.photo = userRequestDTO.getPhoto();
        this.gender = userRequestDTO.getGender();
        this.address = userRequestDTO.getAddress();
    }
}