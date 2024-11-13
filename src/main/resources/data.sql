--STOCK
INSERT INTO Stock (stock_type, quantity) VALUES ( 'Unidad', 5.0);
INSERT INTO Stock (stock_type, quantity) VALUES (  'KG', 40.5);
INSERT INTO Stock (stock_type, quantity) VALUES ( 'Unidad', 1);

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
INSERT INTO order_state(state_code, state_desc) VALUES(1, 'Creada');
INSERT INTO order_state(state_code, state_desc) VALUES(2, 'En proceso');
INSERT INTO order_state(state_code, state_desc) VALUES(3, 'Enviado');

--ORDER
INSERT INTO customer_order(user_id, order_state_id, order_date, confirmation_date) VALUES (1, 1, '12:30:00', '12:45:00');

--ORDER DETAIL
INSERT INTO product_order(order_id, product_id, order_quantity) VALUES(1, 1, 5.0);
INSERT INTO product_order(order_id, product_id, order_quantity) VALUES(1, 2, 2.5)
