package com.ing_software_grupo8.sistema_de_pedidos.service;

import com.ing_software_grupo8.sistema_de_pedidos.DTO.StockDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.ing_software_grupo8.sistema_de_pedidos.entity.Stock;
import com.ing_software_grupo8.sistema_de_pedidos.exception.ApiException;
import com.ing_software_grupo8.sistema_de_pedidos.repository.IStockRepository;
import java.util.Optional;

@Service
public class StockService implements IStockService {

    @Autowired
    private IStockRepository stockRepository;

    public void createStock(Stock stock) {
        if (stock.getQuantity() < 0)
            throw new ApiException(HttpStatus.CONFLICT, "El stock no puede ser negativo");
        stockRepository.save(stock);
    }

    @Override
    public Optional<Stock> getStockFrom(long productId) {
        return stockRepository.findById(productId);
    }

    @Override
    public void editStockFrom(StockDTO stockDTO) {
        Stock stock = stockRepository.findById(stockDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Stock no encontrado"));
        if (stockDTO.getQuantity() < 0)
            throw new ApiException(HttpStatus.CONFLICT, "El stock no puede ser negativo");
        stock.setStockType(stockDTO.getStockType());
        stock.setQuantity(stockDTO.getQuantity());
        stockRepository.save(stock);
    }
}