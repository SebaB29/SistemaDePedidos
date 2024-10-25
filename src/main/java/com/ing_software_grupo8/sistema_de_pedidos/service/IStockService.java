package com.ing_software_grupo8.sistema_de_pedidos.service;

import com.ing_software_grupo8.sistema_de_pedidos.entity.Stock;
import com.ing_software_grupo8.sistema_de_pedidos.security.CustomUserDetails;

public interface IStockService {

    void createStock(CustomUserDetails user, Stock stock);
}
