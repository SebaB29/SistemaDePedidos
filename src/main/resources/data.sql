INSERT INTO Stock (stock_type, quantity) VALUES ( 'Unidad', 5.0);
INSERT INTO Stock (stock_type, quantity) VALUES (  'KG', 40.5);
INSERT INTO Stock (stock_type, quantity) VALUES ( 'Unidad', 1);

INSERT INTO Product (name, stock_id) VALUES ( 'Iphone',1);
INSERT INTO Product (name, stock_id) VALUES ('Pan casero', 2);
INSERT INTO Product (name, stock_id) VALUES ('Monitor 144hz', 3);

INSERT INTO Attribute (product_id, description, attribute_value) VALUES (1,'Memoria', '256GB');
INSERT INTO Attribute (product_id, description, attribute_value) VALUES (1,'Modelo', 'Pro-max');

INSERT INTO users (username, last_name, email, password, age, photo, gender, address, role)
VALUES ('nombre', 'apellido', 'algo@fiuba.com', 'pass123', 25, 'asdasd', 'male', 'av123', 'USER');

INSERT INTO rule (name, type, rule_value) VALUES ('WeightLimitRule', 'WEIGHT_LIMIT', '10.0');
--INSERT INTO rule (name, type, rule_value) VALUES ('AttributeLimitRule', 'ATTRIBUTE_LIMIT', '5');

--INSERT INTO rule (name, type) VALUES ('CompositeAndRule', 'AND');

--INSERT INTO rule_composition (parent_rule_id, child_rule_id) VALUES ((SELECT id FROM rule WHERE name = 'CompositeAndRule'), (SELECT id FROM rule WHERE name = 'WeightLimitRule'));
--INSERT INTO rule_composition (parent_rule_id, child_rule_id) VALUES ((SELECT id FROM rule WHERE name = 'CompositeAndRule'), (SELECT id FROM rule WHERE name = 'AttributeLimitRule'));

