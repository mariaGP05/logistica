CREATE TABLE orders (
    id               BIGSERIAL         PRIMARY KEY,
    client_id        BIGINT            NOT NULL REFERENCES client(id),
    service_id       BIGINT            NOT NULL REFERENCES logistic_service(id),
    origin_city      VARCHAR(150)      NOT NULL,
    destination_city VARCHAR(150)      NOT NULL,
    distance_km      DOUBLE PRECISION  NOT NULL,
    total_cost       NUMERIC(12,2)     NOT NULL,
    status           VARCHAR(30)       NOT NULL DEFAULT 'PENDING',
    created_at       TIMESTAMP         NOT NULL DEFAULT NOW(),
    delivered_at     TIMESTAMP,
    notes            TEXT
);

CREATE INDEX idx_orders_client_id  ON orders(client_id);
CREATE INDEX idx_orders_service_id ON orders(service_id);
CREATE INDEX idx_orders_created_at ON orders(created_at);