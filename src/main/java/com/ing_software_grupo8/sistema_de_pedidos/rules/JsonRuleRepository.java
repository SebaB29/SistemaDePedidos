package com.ing_software_grupo8.sistema_de_pedidos.rules;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonRuleRepository {
    private final List<Rule> rules = new ArrayList<>();

    public JsonRuleRepository() {
        try {
            loadRulesFromJson();
        } catch (IOException e) {
            throw new RuntimeException("Error loading rules from JSON", e);
        }
    }

    private void loadRulesFromJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(new File("src/main/java/com/ing_software_grupo8/sistema_de_pedidos/rules/rules.json"));
        rules.add(parseRule(root));
    }

    private Rule parseRule(JsonNode node) {
        String type = node.get("type").asText();
        switch (type) {
            case "AttributeLimitRule":
                return new AttributeLimitRule(
                        node.get("attributeName").asText(),
                        (float) node.get("maxCount").asDouble()
                );
            case "AttributeValueLimitRule":
                return new AttributeValueLimitRule(
                        node.get("attributeName").asText(),
                        node.get("maxValue").asDouble()
                );
            case "ProductLimitRule":
                return new ProductLimitRule(
                        node.get("productName").asText(),
                        (float) node.get("maxCount").asDouble()
                );
            case "AndRule":
                List<Rule> andSubRules = new ArrayList<>();
                for (JsonNode subRuleNode : node.get("subRules")) {
                    andSubRules.add(parseRule(subRuleNode));
                }
                return new AndRule(andSubRules);
            case "OrRule":
                List<Rule> orSubRules = new ArrayList<>();
                for (JsonNode subRuleNode : node.get("subRules")) {
                    orSubRules.add(parseRule(subRuleNode));
                }
                return new OrRule(orSubRules);
            default:
                throw new IllegalArgumentException("Unknown rule type: " + type);
        }
    }

    public List<Rule> findAll() {
        return rules;
    }
}
