package com.ing_software_grupo8.sistema_de_pedidos.rules;

import com.ing_software_grupo8.sistema_de_pedidos.entity.Order;
import com.ing_software_grupo8.sistema_de_pedidos.entity.Attribute;
import com.ing_software_grupo8.sistema_de_pedidos.entity.ProductOrder;

public class AttributeValueLimitRule implements Rule {

    private final String attributeName;
    private final double maxValue;

    public AttributeValueLimitRule(String attributeName, double maxValue) {
        this.attributeName = attributeName;
        this.maxValue = maxValue;
    }

    @Override
    public boolean validate(Order order) {
        double totalValue = order.getProductOrder().stream()
            .flatMapToDouble(productOrder -> productOrder.getProduct().getAttributes().stream()
                .filter(attribute -> attributeName.equals(attribute.getDescription()))
                .mapToDouble(attribute -> {
                    try {
                        double attributeValue = Double.parseDouble(attribute.getValue());
                        return attributeValue * productOrder.getOrderQuantity();
                    } catch (NumberFormatException e) {
                        return 0.0;
                    }
                })
            )
            .sum();

        return totalValue <= maxValue;
    }
}
