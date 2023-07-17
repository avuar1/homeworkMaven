
CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       first_name VARCHAR(255) NOT NULL ,
                       last_name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE ,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(255) NOT NULL
);

DROP TABLE users CASCADE;


CREATE TABLE customer_data (
                               id SERIAL PRIMARY KEY,
                               user_id INT REFERENCES users (id) NOT NULL UNIQUE ,
                               driver_license_number VARCHAR(255) NOT NULL UNIQUE ,
                               driver_license_expiration DATE NOT NULL,
                               credit_amount NUMERIC(8 , 2) NOT NULL
);

DROP TABLE customer_data CASCADE;

CREATE TABLE car_category
(
    id        SERIAL PRIMARY KEY,
    category_level  VARCHAR(128)  NOT NULL,
    day_price NUMERIC(8, 2) NOT NULL
);

DROP TABLE car_category CASCADE;

CREATE TABLE car
(
    id             SERIAL PRIMARY KEY,
    car_model      VARCHAR(255) NOT NULL,
    category_id    INTEGER NOT NULL,
    colour         VARCHAR(255) NOT NULL,
    seats_quantity INTEGER NOT NULL,
    FOREIGN KEY (category_id) REFERENCES car_category (id)
);

DROP TABLE car CASCADE;


CREATE TABLE orders (
                        id SERIAL PRIMARY KEY,
                        user_id INTEGER NOT NULL,
                        car_id INTEGER NOT NULL,
                        order_status VARCHAR(255) NOT NULL,
                        message VARCHAR(255),
                        FOREIGN KEY (user_id) REFERENCES users(id),
                        FOREIGN KEY (car_id) REFERENCES car(id)
);

DROP TABLE orders CASCADE;

CREATE TABLE rental_time (
                             id SERIAL PRIMARY KEY,
                             car_id INTEGER NOT NULL,
                             start_rental_time TIMESTAMP NOT NULL,
                             end_rental_time TIMESTAMP NOT NULL,
                             order_id INTEGER NOT NULL,
                             FOREIGN KEY (car_id) REFERENCES car(id),
                             FOREIGN KEY (order_id) REFERENCES orders(id)
);

DROP TABLE rental_time CASCADE;