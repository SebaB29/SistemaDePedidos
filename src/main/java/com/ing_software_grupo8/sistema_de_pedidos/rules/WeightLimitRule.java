package com.ing_software_grupo8.sistema_de_pedidos.rules;

import com.ing_software_grupo8.sistema_de_pedidos.entity.Order;
import com.ing_software_grupo8.sistema_de_pedidos.entity.Product;


public class WeightLimitRule implements Rule {

    private final double maxWeight;

    public WeightLimitRule(double maxWeight) {
        this.maxWeight = maxWeight;
    }

    @Override
    public boolean validate(Order order) {
        double totalWeight = order.getProductOrder().stream()
            .mapToDouble(productOrder -> {
                Product product = productOrder.getProduct();
                
                double weight = product.getAttributes().stream()
                    .filter(attribute -> "weight".equals(attribute.getDescription())) 
                    .mapToDouble(attribute -> Double.valueOf(attribute.getValue()))
                    .findFirst()
                    .orElse(0.0);

                return weight * productOrder.getOrderQuantity();
            })
            .sum();

        return totalWeight <= maxWeight;
    }
}