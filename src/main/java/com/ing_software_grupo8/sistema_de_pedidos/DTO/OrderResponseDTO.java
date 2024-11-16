package com.ing_software_grupo8.sistema_de_pedidos.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class OrderResponseDTO {

    @JsonProperty(value = "order_state")
    private String orderState;

    @JsonProperty(value = "creation_date")
    private String creationDate;

    @JsonProperty(value = "product_list")
    private List<ProductResponseDTO> productDTOList;
}
