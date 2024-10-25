package com.ing_software_grupo8.sistema_de_pedidos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ing_software_grupo8.sistema_de_pedidos.entity.Stock;
import com.ing_software_grupo8.sistema_de_pedidos.repository.IStockRepository;
import com.ing_software_grupo8.sistema_de_pedidos.security.CustomUserDetails;

@Service
public class StockService implements IStockService {

    @Autowired
    private IStockRepository stockRepository;

    public void createStock(CustomUserDetails user, Stock stock) {
        if (!user.isAdmin())
            throw new IllegalArgumentException("Only admins can create Stocks to products");
        stockRepository.save(stock);
    }

}
