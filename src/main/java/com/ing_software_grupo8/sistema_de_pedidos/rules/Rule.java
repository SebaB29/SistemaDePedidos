package com.ing_software_grupo8.sistema_de_pedidos.rules;

import com.ing_software_grupo8.sistema_de_pedidos.entity.Order;

public interface Rule {
    boolean validate(Order order);
}
