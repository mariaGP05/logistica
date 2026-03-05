package com.maria.logistica.domain.port.input;

import com.maria.logistica.domain.model.Vehicle;
import java.util.Optional;

public interface UpdateVehicleUseCase {
    Optional<Vehicle> execute(String licensePlate, Vehicle vehicle);
}

