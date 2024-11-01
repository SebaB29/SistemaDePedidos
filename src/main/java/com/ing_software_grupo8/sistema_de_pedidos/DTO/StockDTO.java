package com.ing_software_grupo8.sistema_de_pedidos.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockDTO {
    private long stockId;
    private String stockType;
    private long productId;
    private float quantity;
}