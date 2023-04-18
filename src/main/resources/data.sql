insert into stocks (id, name) values (1, 'warehouse1');

insert into products (id, name, price) values
(1, 'product1', 10.00),
(2, 'product2', 10.00),
(3, 'product3', 5.00);

insert into stock_quantity (id, quantity, quantity_value, product_id, stock_id) values
(1, 10, 100.00, 1, 1),
(2, 15, 150.00, 2, 1);

insert into orders (id, net) values (1, 70.00);

insert into order_items (id, net, quantity, client_order_id, product_id) values
(1, 20.00, 2, 1, 1),
(2, 50.00, 5, 1, 2),
(3, 30.00, 6, 1, 3);

