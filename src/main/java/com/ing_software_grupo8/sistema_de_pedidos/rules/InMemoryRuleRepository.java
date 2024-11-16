package com.ing_software_grupo8.sistema_de_pedidos.rules;

import java.util.ArrayList;
import java.util.List;

public class InMemoryRuleRepository {
    private List<Rule> rules = new ArrayList<>();

    public InMemoryRuleRepository() {

        List<Rule> andChildren = new ArrayList<>();
        andChildren.add(new AttributeLimitRule("alcoholType", 10));

        andChildren.add(new AttributeValueLimitRule("peso", 150.0));

        rules.add(new AndRule(andChildren)); // La orden no puede tener más de tipos de alcohol diferentes y el peso total debe ser menor a 50.0
    }

    public List<Rule> findAll() {
        return rules;
    }
}
