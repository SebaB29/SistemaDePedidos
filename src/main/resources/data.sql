--USER
INSERT INTO users (username, last_name, email, password, age, photo, gender, address, role)
VALUES
    ('UserAdmin', 'AdminLastName', 'admin@gmail.com', 'pass123', 40, 'https://photoAdmin.png/', 'male', 'calle 123', 'ADMIN'),
    ('NormalUser', 'UserLastName', 'normalUser@fiuba.com', 'pass123', 25, 'https://photoNormalUser.png/', 'male', 'calle 124', 'USER');

--PRODUCT
INSERT INTO Product (name, stock_id)
VALUES
    ('Pesa',1),
    ('Remera',2),
    ('Cerveza',3),
    ('Asado',4);

--ATTRIBUTE
INSERT INTO Attribute (product_id, description, attribute_value)
VALUES
    (1,'peso', '10'),
    (2,'material', 'algodón'),
    (3,'alcoholType', 'negra'),
    (4,'corte', 'banderita');

--STOCK
INSERT INTO Stock (stock_type, quantity)
VALUES
    ('Unidades', 100.0),
    ('Unidades', 100.0),
    ('Unidades', 20.0),
    ('KG', 10.0);

--ORDER STATE
INSERT INTO order_state(state_code, state_desc)
VALUES
    (0, 'Confirmado'),
    (1, 'En proceso'),
    (2, 'Enviado'),
    (3, 'Cancelado');

--ORDER
INSERT INTO customer_order(user_id, order_state_id, order_date)
VALUES
    (2, 1, '2024-11-14T14:35:10.123');

--ORDER DETAIL
INSERT INTO product_order(order_id, product_id, order_quantity)
VALUES
    (1, 1, 3.0),
    (1, 2, 2.0);