package com.ing_software_grupo8.sistema_de_pedidos.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class OrderListDTO {

    @JsonProperty(value = "order_list")
    public List<OrderResponseDTO> orderResponseDTOList;
}
