package com.ing_software_grupo8.sistema_de_pedidos.rules;

import com.ing_software_grupo8.sistema_de_pedidos.entity.Order;

import java.util.List;

public class AndRule implements Rule {

    private final List<Rule> subRules;

    public AndRule(List<Rule> rules) {
        this.subRules = rules;
    }

    @Override
    public boolean validate(Order order) {
        return subRules.stream().allMatch(rule -> rule.validate(order));
    }
}