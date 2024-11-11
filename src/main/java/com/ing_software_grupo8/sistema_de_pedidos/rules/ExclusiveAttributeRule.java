package com.ing_software_grupo8.sistema_de_pedidos.rules;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ing_software_grupo8.sistema_de_pedidos.entity.Order;

public class ExclusiveAttributeRule implements Rule {

    private final Set<String> exclusiveAttributes;

    public ExclusiveAttributeRule(List<String> exclusiveAttributes) {
        this.exclusiveAttributes = new HashSet<>(exclusiveAttributes);
    }

    @Override
    public boolean validate(Order order) {
        Set<String> foundAttributes = new HashSet<>();

        for (var productOrder : order.getProductOrder()) {
            var productAttributes = productOrder.getProduct().getAttributes();

            for (var attribute : productAttributes) {
                String attributeDescription = attribute.getDescription();

                if (exclusiveAttributes.contains(attributeDescription)) {
                    if (!foundAttributes.isEmpty() && !foundAttributes.contains(attributeDescription)) {
                        return false;
                    }
                    foundAttributes.add(attributeDescription);
                }
            }
        }
        return true;
    }
}
