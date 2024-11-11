package com.ing_software_grupo8.sistema_de_pedidos.rules;

import java.util.ArrayList;
import java.util.List;

public class InMemoryRuleRepository {
    private List<Rule> rules = new ArrayList<>();

    public InMemoryRuleRepository() {
        rules.add(new WeightLimitRule(10.0));
        rules.add(new AttributeLimitRule("quantity", 5));

        List<Rule> andChildren = new ArrayList<>();
        andChildren.add(new WeightLimitRule(10.0));
        andChildren.add(new AttributeLimitRule("quantity", 5));
        andChildren.add(new ExclusiveAttributeRule(List.of("TipoPan", "Modelo")));
        rules.add(new AndRule(andChildren));
    }

    public List<Rule> findAll() {
        return rules;
    }
}

