package com.ing_software_grupo8.sistema_de_pedidos.service;

import com.ing_software_grupo8.sistema_de_pedidos.entity.Attribute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ing_software_grupo8.sistema_de_pedidos.repository.IAttributeRepository;

@Service
public class AttributeService implements IAttributeService {

    @Autowired
    private IAttributeRepository attributeRepository;

    public void createAttribute(Attribute attribute) {
        // if (!user.isAdmin())
        // throw new IllegalArgumentException("Only admins can create attribute to
        // products");
        attributeRepository.save(attribute);
    }
}
