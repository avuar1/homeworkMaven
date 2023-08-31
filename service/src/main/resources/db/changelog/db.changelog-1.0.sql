--liquibase formatted sql

--changeset avuar1:2
CREATE TABLE IF NOT EXISTS users
(
    id         SERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    role       VARCHAR(255) NOT NULL
);

-- DROP TABLE users CASCADE;

--changeset avuar1:3
CREATE TABLE IF NOT EXISTS customer_data
(
    id                        SERIAL PRIMARY KEY,
    user_id                   INT REFERENCES users (id) NOT NULL UNIQUE,
    driver_license_number     VARCHAR(255)              NOT NULL UNIQUE,
    driver_license_expiration DATE                      NOT NULL,
    credit_amount             NUMERIC(8, 2)             NOT NULL
);


--changeset avuar1:4
CREATE TABLE IF NOT EXISTS car_category
(
    id             SERIAL PRIMARY KEY,
    category_level VARCHAR(128)  NOT NULL,
    day_price      NUMERIC(8, 2) NOT NULL
);

--changeset avuar1:5
CREATE TABLE IF NOT EXISTS car
(
    id             SERIAL PRIMARY KEY,
    car_model      VARCHAR(255)                     NOT NULL,
    category_id    INT REFERENCES car_category (id) NOT NULL UNIQUE,
    colour         VARCHAR(255)                     NOT NULL,
    seats_quantity INTEGER                          NOT NULL,
    image          VARCHAR(128)                     NOT NULL
);

--changeset avuar1:6
CREATE TABLE IF NOT EXISTS orders
(
    id           SERIAL PRIMARY KEY,
    user_id      INTEGER REFERENCES users (id) NOT NULL UNIQUE,
    car_id       INTEGER REFERENCES car (id)   NOT NULL UNIQUE,
    order_status VARCHAR(255)                  NOT NULL,
    message      TEXT
);

--changeset avuar1:7
CREATE TABLE IF NOT EXISTS rental_time
(
    id                SERIAL PRIMARY KEY,
    car_id            INTEGER REFERENCES car (id)    NOT NULL,
    start_rental_time TIMESTAMP                      NOT NULL,
    end_rental_time   TIMESTAMP                      NOT NULL,
    order_id          INTEGER REFERENCES orders (id) NOT NULL

);