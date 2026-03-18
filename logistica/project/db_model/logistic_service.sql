CREATE TABLE logistic_service (
    id            BIGSERIAL      PRIMARY KEY,
    name          VARCHAR(100)   NOT NULL UNIQUE,
    type          VARCHAR(30)    NOT NULL,
    price_per_km  NUMERIC(10,2)  NOT NULL,
    description   TEXT,
    available     BOOLEAN        NOT NULL DEFAULT TRUE
);