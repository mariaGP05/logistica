CREATE TABLE route (
    id SERIAL PRIMARY KEY,
    origin VARCHAR(255) NOT NULL,
    destination VARCHAR(255) NOT NULL,
    distance DOUBLE PRECISION NOT NULL,
    duration INTEGER NOT NULL,
    shipment_date DATE NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    vehicle_assigned_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (vehicle_assigned_id) REFERENCES vehicle(id) ON DELETE SET NULL
);

CREATE INDEX idx_status ON route(status);
CREATE INDEX idx_shipment_date ON route(shipment_date);

