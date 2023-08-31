INSERT INTO users (first_name, last_name, email, password, role)
VALUES ('Maksim', 'Petrov', 'maks@gmail.com', '123456', 'CLIENT'),
       ('Aleksey', 'Smirnov', 'aleks@gmail.com', '123456', 'CLIENT'),
       ('Sergey', 'Ivanov', 'sergey@gmail.com', '123456', 'CLIENT');

INSERT INTO customer_data (user_id, driver_license_number, driver_license_expiration, credit_amount)
VALUES ((SELECT id FROM users WHERE last_name = 'Petrov'), '22342', '2024-3-23', 4500.0),
       ((SELECT id FROM users WHERE last_name = 'Smirnov'), '343', '2025-6-10', 1000.0),
       ((SELECT id FROM users WHERE last_name = 'Ivanov'), '225435442', '2023-3-23', 4500.0);

INSERT INTO car_category (category_level, day_price)
VALUES ('ECONOM', 50.0),
       ('PREMIUM', 70.0),
       ('SEDAN', 60.0),
       ('MIDLE', 55.0),
       ('SUV', 65.0);

INSERT INTO car (car_model, category_id, colour, seats_quantity)
VALUES ('OPEL', (SELECT id FROM car_category WHERE category_level = 'ECONOM'), 'WHITE', 5),
       ('MERSEDES', (SELECT id FROM car_category WHERE category_level = 'SEDAN'), 'BLACK', 5),
       ('TOYOTA', (SELECT id FROM car_category WHERE category_level = 'SUV'), 'WHITE', 7),
       ('LAMBORGINI', (SELECT id FROM car_category WHERE category_level = 'PREMIUM'), 'WHITE', 7),
       ('SHKODA', (SELECT id FROM car_category WHERE category_level = 'MIDLE'), 'BLACK', 5),
       ('CHERRY', (SELECT id FROM car_category WHERE category_level = 'MIDLE'), 'RED', 5);

INSERT INTO orders (user_id, car_id, order_status, message)
VALUES ((SELECT id FROM users WHERE last_name = 'Petrov'), (SELECT id FROM car WHERE car_model = 'TOYOTA RAV4'),
        'ACCEPTED', 'super'),
       ((SELECT id FROM users WHERE last_name = 'Smirnov'), (SELECT id FROM car WHERE car_model = 'MAZDA CX-9'),
        'ACCEPTED', 'otlichno'),
       ((SELECT id FROM users WHERE last_name = 'Ivanov'), (SELECT id FROM car WHERE car_model = 'MAZDA CX-9'),
        'ACCEPTED', 'velicolepno'),
       ((SELECT id FROM users WHERE last_name = 'Petrov'), (SELECT id FROM car WHERE car_model = 'MAZDA 6'),
        'CANCELED', 'net deneg');

INSERT INTO rental_time (car_id, start_rental_time, end_rental_time, order_id)
VALUES ((SELECT id FROM car WHERE car_model = 'TOYOTA RAV4'), '2020-1-25 12:0', '2020-1-29 18:0',
        (SELECT id
         FROM orders
         WHERE orders.car_id = (SELECT id FROM car WHERE car_model = 'TOYOTA RAV4')
           AND orders.user_id = (SELECT id FROM users WHERE last_name = 'Petrov'))),
       ((SELECT id FROM car WHERE car_model = 'MAZDA CX-9'), '2020-2-25 12:0', '2020-2-28 12:0',
        (SELECT id
         FROM orders
         WHERE orders.car_id = (SELECT id FROM car WHERE car_model = 'MAZDA CX-9')
           AND orders.user_id = (SELECT id FROM users WHERE last_name = 'Smirnov')));
--        ((SELECT id FROM car WHERE car_model = 'MAZDA CX-9'), '2020-5-25 12:0', '2020-5-28 12:0',
--         (SELECT id
--          FROM orders
--          WHERE orders.car_id = (SELECT id FROM car WHERE car_model = 'MAZDA CX-9')
--            AND orders.user_id = (SELECT id FROM users WHERE last_name = 'Ivanov')));
--        ((SELECT id FROM car WHERE car_model = 'MAZDA 6'), '2020-3-25 12:0', '2020-3-28 12:0',
--         (SELECT id
--          FROM orders
--          WHERE orders.car_id = (SELECT id FROM car WHERE car_model = 'MAZDA 6')
--            AND orders.user_id = (SELECT id FROM users WHERE last_name = 'Petrov')));
