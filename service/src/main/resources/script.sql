CREATE TABLE users
(
    id         SERIAL PRIMARY KEY,
    first_name VARCHAR(128) NOT NULL,
    last_name  VARCHAR(128) NOT NULL,
    email      VARCHAR(256) NOT NULL UNIQUE,
    password   VARCHAR(128) NOT NULL,
    role       VARCHAR(32)  NOT NULL
);

DROP TABLE users CASCADE;


CREATE TABLE customer_data
(
    id                        SERIAL PRIMARY KEY,
    user_id                   INT REFERENCES users (id) NOT NULL UNIQUE,
    driver_licence_number     VARCHAR(32)               NOT NULL UNIQUE,
    driver_license_expiration DATE                      NOT NULL,
    credit_amount             NUMERIC(8, 2)             NOT NULL
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
    model          VARCHAR(32)                      NOT NULL,
    category_id    INT REFERENCES car_category (id) NOT NULL,
    colour         VARCHAR(32)                      NOT NULL,
    seats_quantity INT                              NOT NULL
);


DROP TABLE car CASCADE;


CREATE TABLE orders
(
    id           SERIAL PRIMARY KEY,
    user_id      INTEGER,
    car_id       INTEGER,
    order_status VARCHAR(255),
    message      VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (car_id) REFERENCES car (id)
);

DROP TABLE orders CASCADE;

CREATE TABLE rental_time (
                             id SERIAL PRIMARY KEY,
                             car_id INTEGER REFERENCES car(id),
                             start_rental_time TIMESTAMP,
                             end_rental_time TIMESTAMP,
                             order_id INTEGER REFERENCES orders(id)
);

DROP TABLE rental_time CASCADE;