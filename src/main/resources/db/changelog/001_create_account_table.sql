--liquibase formatted sql
--changeset keyj148:001-create-account-table

CREATE TABLE account(
    id UUID PRIMARY KEY
);

