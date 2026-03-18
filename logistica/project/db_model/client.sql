CREATE TABLE client (
    id            BIGSERIAL     PRIMARY KEY,
    first_name    VARCHAR(100)  NOT NULL,
    last_name     VARCHAR(150)  NOT NULL,
    email         VARCHAR(255)  NOT NULL UNIQUE,
    phone         VARCHAR(30)   NOT NULL,
    address       VARCHAR(300)  NOT NULL,
    registered_at TIMESTAMP     NOT NULL DEFAULT NOW(),
    active        BOOLEAN       NOT NULL DEFAULT TRUE
);

CREATE INDEX idx_client_email ON client(email);