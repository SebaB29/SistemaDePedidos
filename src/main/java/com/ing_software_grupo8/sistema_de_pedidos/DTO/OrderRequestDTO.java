package com.ing_software_grupo8.sistema_de_pedidos.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {

    @JsonProperty(value = "user_id")
    private Long userId;

    @JsonProperty(value = "order_id")
    private Long orderId;

    @JsonProperty(value = "order_state")
    private Integer orderState;

    @JsonProperty(value = "products")
    private List<ProductOrderDTO> productOrderDTOList;
}
