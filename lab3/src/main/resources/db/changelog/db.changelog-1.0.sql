--liquibase formatted sql

--changeset agusev:1
CREATE TABLE IF NOT EXISTS street
(
    id      BIGSERIAL PRIMARY KEY,
    name    VARCHAR(64) NOT NULL,
    post_id INT         NOT NULL
);

--changeset agusev:2
CREATE TABLE IF NOT EXISTS house
(
    id            BIGSERIAL PRIMARY KEY,
    name          VARCHAR(64)                   NOT NULL,
    build_date    DATE                          NOT NULL,
    floors_number INT                           NOT NULL,
    building_type VARCHAR(64),
    street_id     BIGINT REFERENCES street (id) NOT NULL
);

--changeset agusev:3
CREATE TABLE IF NOT EXISTS flat
(
    id          BIGSERIAL PRIMARY KEY,
    number      INT                          NOT NULL,
    square      INT                          NOT NULL,
    room_number INT                          NOT NULL,
    house_id    BIGINT REFERENCES house (id) NOT NULL
)