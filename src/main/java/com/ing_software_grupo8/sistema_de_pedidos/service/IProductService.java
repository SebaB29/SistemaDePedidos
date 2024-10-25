package com.ing_software_grupo8.sistema_de_pedidos.service;

import com.ing_software_grupo8.sistema_de_pedidos.entity.Product;
import com.ing_software_grupo8.sistema_de_pedidos.security.CustomUserDetails;

public interface IProductService {

    long createProduct(CustomUserDetails user, Product product);
}
