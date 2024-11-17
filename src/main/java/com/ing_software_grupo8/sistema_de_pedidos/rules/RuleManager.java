package com.ing_software_grupo8.sistema_de_pedidos.rules;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ing_software_grupo8.sistema_de_pedidos.entity.Order;

@Service
public class RuleManager {
    private List<Rule> allRules;

    public RuleManager() {
<<<<<<< HEAD
        JsonRuleRepository ruleRepository = new JsonRuleRepository();
=======
        InMemoryRuleRepository ruleRepository = new InMemoryRuleRepository();
>>>>>>> 87cbdc2a814bfd717b4e3c82898c107bfb93459b
        this.allRules = ruleRepository.findAll();
    }

    public boolean validateOrder(Order order) {
        return allRules.stream().allMatch(rule -> rule.validate(order));
    }

}
