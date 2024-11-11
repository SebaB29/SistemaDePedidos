package com.ing_software_grupo8.sistema_de_pedidos.rules;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface RuleRepository extends JpaRepository<RuleEntity, Long> {
}
