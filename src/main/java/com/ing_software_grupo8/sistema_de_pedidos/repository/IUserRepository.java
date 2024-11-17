package com.ing_software_grupo8.sistema_de_pedidos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ing_software_grupo8.sistema_de_pedidos.entity.User;

public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
}
