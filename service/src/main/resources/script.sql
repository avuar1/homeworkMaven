
CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       first_name VARCHAR(255),
                       last_name VARCHAR(255),
                       email VARCHAR(255),
                       password VARCHAR(255),
                       role VARCHAR(255),
                       customer_data_id INTEGER,
                       FOREIGN KEY (customer_data_id) REFERENCES customer_data(id)
);

DROP TABLE users CASCADE;


CREATE TABLE customer_data (
                               id SERIAL PRIMARY KEY,
                               user_id INTEGER,
                               driver_license_number VARCHAR(255) UNIQUE,
                               driver_license_expiration DATE,
                               credit_amount NUMERIC(8 , 2) NOT NULL
);

DROP TABLE customer_data CASCADE;

CREATE TABLE car_category
(
    id        SERIAL PRIMARY KEY,
    category  VARCHAR(128)  NOT NULL UNIQUE,
    day_price NUMERIC(8, 2) NOT NULL
);

DROP TABLE car_category CASCADE;

CREATE TABLE car
(
    id             SERIAL PRIMARY KEY,
    car_model      VARCHAR(255),
    category_id    INTEGER,
    colour         VARCHAR(255),
    seats_quantity INTEGER,
    FOREIGN KEY (category_id) REFERENCES car_category (id)
);

DROP TABLE car CASCADE;


CREATE TABLE orders (
                        id SERIAL PRIMARY KEY,
                        user_id INTEGER,
                        car_id INTEGER,
                        order_status VARCHAR(255),
                        message VARCHAR(255),
                        FOREIGN KEY (user_id) REFERENCES users(id),
                        FOREIGN KEY (car_id) REFERENCES car(id)
);

DROP TABLE orders CASCADE;

CREATE TABLE rental_time (
                             id SERIAL PRIMARY KEY,
                             car_id INTEGER,
                             start_rental_time TIMESTAMP,
                             end_rental_time TIMESTAMP,
                             order_id INTEGER,
                             FOREIGN KEY (car_id) REFERENCES car(id),
                             FOREIGN KEY (order_id) REFERENCES orders(id)
);

DROP TABLE rental_time CASCADE;