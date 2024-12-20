package com.ing_software_grupo8.sistema_de_pedidos.service;

import com.ing_software_grupo8.sistema_de_pedidos.DTO.StockDTO;
import com.ing_software_grupo8.sistema_de_pedidos.entity.Stock;
import com.ing_software_grupo8.sistema_de_pedidos.repository.IStockRepository;
import com.ing_software_grupo8.sistema_de_pedidos.exception.ApiException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.Optional;

@Service
public class StockService implements IStockService {

    @Autowired
    private IStockRepository stockRepository;

    @Override
    public void createStock(Stock stock) {
        validateNonNegativeStock(stock.getQuantity());
        stockRepository.save(stock);
    }

    @Transactional
    @Override
    public void updateStock(StockDTO stockDTO) {
        Stock stock = stockRepository.findById(stockDTO.getProductId())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Stock no encontrado"));

        validateNonNegativeStock(stockDTO.getQuantity());
        stock.setStockType(stockDTO.getStockType());
        stock.setQuantity(stockDTO.getQuantity());
        stockRepository.save(stock);
    }

    @Override
    public Optional<Stock> getStockFrom(long productId) {
        return stockRepository.findById(productId);
    }

    // Métodos auxiliares
    private void validateNonNegativeStock(float quantity) {
        if (quantity < 0) {
            throw new ApiException(HttpStatus.CONFLICT, "El stock no puede ser negativo");
        }
    }
}
