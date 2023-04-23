CREATE DATABASE tech;
DROP DATABASE tech;

CREATE TABLE street
(
    id      BIGSERIAL PRIMARY KEY,
    name    VARCHAR(64) NOT NULL,
    post_id INT         NOT NULL
);

CREATE TABLE house
(
    id            BIGSERIAL PRIMARY KEY,
    name          VARCHAR(64)                   NOT NULL,
    build_date    DATE                          NOT NULL,
    floors_number INT                           NOT NULL,
    building_type VARCHAR(64),
    street_id     BIGINT REFERENCES street (id) NOT NULL UNIQUE
);