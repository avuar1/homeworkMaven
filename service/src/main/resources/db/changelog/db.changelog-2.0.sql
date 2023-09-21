--liquibase formatted sql

--changeset avuar1:1
ALTER TABLE orders
    ADD COLUMN begin_time TIMESTAMP NOT NULL,
    ADD COLUMN end_time TIMESTAMP NOT NULL;
-- CREATE TABLE IF NOT EXISTS orders
-- (
--     id           SERIAL PRIMARY KEY,
--     user_id      INTEGER REFERENCES users (id) NOT NULL UNIQUE,
--     car_id       INTEGER REFERENCES car (id)   NOT NULL UNIQUE,
--     begin_time   TIMESTAMP                 NOT NULL,
--     end_time     TIMESTAMP                 NOT NULL,
--     order_status VARCHAR(255)                  NOT NULL,
--     message      TEXT
-- );
