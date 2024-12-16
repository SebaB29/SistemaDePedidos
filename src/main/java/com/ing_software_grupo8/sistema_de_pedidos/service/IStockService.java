package com.ing_software_grupo8.sistema_de_pedidos.service;

import com.ing_software_grupo8.sistema_de_pedidos.DTO.StockDTO;
import com.ing_software_grupo8.sistema_de_pedidos.entity.Stock;

import java.util.Optional;

public interface IStockService {

    void createStock(Stock stock);

    void updateStock(StockDTO stockDTO);

    Optional<Stock> getStockFrom(long productId);
}
