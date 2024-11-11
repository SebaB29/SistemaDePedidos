package com.ing_software_grupo8.sistema_de_pedidos.rules;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "rules")
public class RuleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rule_type")
    private String ruleType;  // "WeightLimit", "AttributeLimit", "And", "Or"

    private String attribute;
    private Float limitValue;

    @ManyToMany
    @JoinTable(
        name = "rule_composition",
        joinColumns = @JoinColumn(name = "parent_rule_id"),
        inverseJoinColumns = @JoinColumn(name = "child_rule_id")
    )
    private List<RuleEntity> subRules;
}
