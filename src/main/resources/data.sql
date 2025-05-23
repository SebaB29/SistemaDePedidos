--USER
INSERT INTO users (username, last_name, email, password, age, photo, gender, address, role)
VALUES
    ('UserAdmin', 'AdminLastName', 'admin@gmail.com', 'pass123', 40, 'https://as2.ftcdn.net/v2/jpg/03/49/49/79/1000_F_349497933_Ly4im8BDmHLaLzgyKg2f2yZOvJjBtlw5.jpg', 'male', 'calle 123', 'ADMIN'),
    ('NormalUser', 'UserLastName', 'normalUser@fiuba.com', 'pass123', 25, 'https://as2.ftcdn.net/v2/jpg/03/49/49/79/1000_F_349497933_Ly4im8BDmHLaLzgyKg2f2yZOvJjBtlw5.jpg', 'male', 'calle 124', 'USER');

--STOCK
INSERT INTO Stock (stock_type, quantity)
VALUES
    ('Unidades', 100.0),
    ('Unidades', 100.0),
    ('Unidades', 30.0),
    ('KG', 10.0),
    ('Unidades', 40.0),
    ('Unidades', 30.0),
    ('Unidades', 30.0);

--PRODUCT
INSERT INTO Product (name, stock_id, price)
VALUES
    ('Pesa',1, 10),
    ('Remera',2, 25),
    ('Cerveza',3, 15),
    ('Asado',4, 120),
    ('Tequila', 5, 45),
    ('Fernet', 6, 60),
    ('Vodka', 7, 30);

--ATTRIBUTE
INSERT INTO Attribute (product_id, description, attribute_value)
VALUES
    (1,'peso', '10'),
    (2,'material', 'algodón'),
    (3,'alcoholType', 'negra'),
    (4,'corte', 'banderita'),
    (3,'peso', '20.0'),
    (5,'peso', '20.0'),
    (6,'peso', '30.0'),
    (7,'peso', '40.0'),
    (5,'alcoholType', 'Blanco'),
    (6,'alcoholType', 'Hierbas'),
    (7,'alcoholType', 'Blanco');

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