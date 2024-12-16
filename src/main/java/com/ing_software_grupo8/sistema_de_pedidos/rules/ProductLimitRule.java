package com.ing_software_grupo8.sistema_de_pedidos.rules;

import com.ing_software_grupo8.sistema_de_pedidos.entity.Order;
import com.ing_software_grupo8.sistema_de_pedidos.entity.ProductOrder;

public class ProductLimitRule implements Rule {

    private final String productName;
    private final float maxCount;

    public ProductLimitRule(String productName, float maxCount) {
        this.productName = productName;
        this.maxCount = maxCount;
    }

    @Override
    public boolean validate(Order order) {
        double totalProductCount = order.getProductOrder().stream()
                .filter(productOrder -> this.productName.equals(productOrder.getProduct().getName()))
                .mapToDouble(ProductOrder::getOrderQuantity)
                .sum();

        return totalProductCount <= maxCount;
    }
}
