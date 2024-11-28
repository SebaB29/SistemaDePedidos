package com.ing_software_grupo8.sistema_de_pedidos.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductOrderDTO {

    @JsonProperty(value = "product_id", required = true)
    private long productId;

    @JsonProperty(required = true)
    private float quantity;
}
