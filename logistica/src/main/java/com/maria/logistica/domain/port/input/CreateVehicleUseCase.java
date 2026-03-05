package com.maria.logistica.domain.port.input;

import com.maria.logistica.domain.model.Vehicle;

public interface CreateVehicleUseCase {
    Vehicle execute(Vehicle vehicle);
}

