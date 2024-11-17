package com.ing_software_grupo8.sistema_de_pedidos.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SecondaryRow;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {

    @JsonProperty(required = true)
    private Long userId;

    @JsonProperty(required = true)
    private List<ProductOrderDTO> productOrderDTOList;
}
