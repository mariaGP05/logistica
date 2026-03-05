package com.maria.logistica.domain.port.output;

import com.maria.logistica.domain.model.Vehicle;
import java.util.List;
import java.util.Optional;

public interface VehicleRepositoryPort {
    List<Vehicle> findAll();
    Optional<Vehicle> findByLicensePlate(String licensePlate);
    Vehicle save(Vehicle vehicle);
    void deleteByLicensePlate(String licensePlate);
}

