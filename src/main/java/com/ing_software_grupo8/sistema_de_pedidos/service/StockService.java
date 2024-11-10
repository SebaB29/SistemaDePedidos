package com.ing_software_grupo8.sistema_de_pedidos.service;

import com.ing_software_grupo8.sistema_de_pedidos.DTO.StockDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ing_software_grupo8.sistema_de_pedidos.entity.Stock;
import com.ing_software_grupo8.sistema_de_pedidos.repository.IStockRepository;
import java.util.Optional;

@Service
public class StockService implements IStockService {

    @Autowired
    private IStockRepository stockRepository;

    public void createStock(Stock stock) {
        // if (!user.isAdmin())
        // throw new IllegalArgumentException("Only admins can create Stocks to
        // products");
        stockRepository.save(stock);
    }

    @Override
    public Optional<Stock> getStockFrom(long productId){
        return stockRepository.findById(productId);
    }

    @Override
    public void editStockFrom(StockDTO stockDTO){
        Stock stock = stockRepository.findById(stockDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Stock no encontrado"));

        stock.setStockType(stockDTO.getStockType());
        stock.setProductId(stockDTO.getProductId());
        stock.setQuantity(stockDTO.getQuantity());

        stockRepository.save(stock);
    }
}