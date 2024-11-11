package com.ing_software_grupo8.sistema_de_pedidos.rules;

import com.ing_software_grupo8.sistema_de_pedidos.entity.Order;

public class AttributeLimitRule implements Rule {

    private final String attribute;
    private final float maxCount;

    public AttributeLimitRule(String attribute, float maxCount) {
        this.attribute = attribute;
        this.maxCount = maxCount;
    }

    @Override
    public boolean validate(Order order) {
        long totalOccurrences = order.getProductOrder().stream()
            .flatMap(productOrder -> productOrder.getProduct().getAttributes().stream())
            .filter(attribute -> this.attribute.equals(attribute.getDescription()))
            .count();

        return totalOccurrences <= maxCount;
    }
}
