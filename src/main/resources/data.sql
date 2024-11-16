--STOCK
INSERT INTO Stock (stock_type, quantity) VALUES ( 'Unidades', 5.0);
INSERT INTO Stock (stock_type, quantity) VALUES (  'KG', 40.5);
INSERT INTO Stock (stock_type, quantity) VALUES ( 'Unidades', 1);

--PRODUCT
INSERT INTO Product (name, stock_id) VALUES ( 'Iphone',1);
INSERT INTO Product (name, stock_id) VALUES ('Pan casero', 2);
INSERT INTO Product (name, stock_id) VALUES ('Monitor 144hz', 3);

--ATTRIBUTE
INSERT INTO Attribute (product_id, description, attribute_value) VALUES (1,'Memoria', '256GB');
INSERT INTO Attribute (product_id, description, attribute_value) VALUES (1,'Modelo', 'Pro-max');

--USER
INSERT INTO users (username, last_name, email, password, age, photo, gender, address, role)
VALUES ('nombre', 'apellido', 'algo@fiuba.com', 'pass123', 25, 'asdasd', 'male', 'av123', 'USER');

--ORDER STATE
INSERT INTO order_state(state_code, state_desc) VALUES(0, 'Confirmado');
INSERT INTO order_state(state_code, state_desc) VALUES(1, 'En proceso');
INSERT INTO order_state(state_code, state_desc) VALUES(2, 'Enviado');
INSERT INTO order_state(state_code, state_desc) VALUES(3, 'Cancelado');

--ORDER
INSERT INTO customer_order(user_id, order_state_id, order_date) VALUES (1, 1, '2024-11-14T14:35:10.123');

--ORDER DETAIL
INSERT INTO product_order(order_id, product_id, order_quantity) VALUES(1, 1, 5.0);
INSERT INTO product_order(order_id, product_id, order_quantity) VALUES(1, 2, 2.5)