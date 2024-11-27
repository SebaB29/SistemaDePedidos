package com.ing_software_grupo8.sistema_de_pedidos.rules;

import java.util.List;

import com.ing_software_grupo8.sistema_de_pedidos.entity.Order;

public class OrRule implements Rule {
    
    private List<Rule> subRules;

    public OrRule(List<Rule> rules) {
        this.subRules = rules;
    }

    @Override
    public boolean validate(Order order) {
        return subRules.stream().anyMatch(rule -> rule.validate(order));
    }
}