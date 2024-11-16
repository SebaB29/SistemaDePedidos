package com.ing_software_grupo8.sistema_de_pedidos.rules;
import java.util.List;

import com.ing_software_grupo8.sistema_de_pedidos.entity.Order;

public class AndRule implements Rule {

    private List<Rule> subRules;

    public AndRule(List<Rule> rules) {
        this.subRules = rules;
    }

    @Override
    public boolean validate(Order order) {
        return subRules.stream().allMatch(rule -> rule.validate(order));
    }
}