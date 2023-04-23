--liquibase formatted sql

--changeset agusev:1
ALTER TABLE house
ADD COLUMN material VARCHAR(64)