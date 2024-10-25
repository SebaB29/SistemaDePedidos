package com.ing_software_grupo8.sistema_de_pedidos.service;

import com.ing_software_grupo8.sistema_de_pedidos.entity.Attribute;
import com.ing_software_grupo8.sistema_de_pedidos.security.CustomUserDetails;

public interface IAttributeService {

    void createAttribute(CustomUserDetails user, Attribute attribute);
}
