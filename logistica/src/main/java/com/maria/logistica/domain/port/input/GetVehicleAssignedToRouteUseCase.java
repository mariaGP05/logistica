package com.maria.logistica.domain.port.input;

import com.maria.logistica.domain.model.Vehicle;
import java.util.Optional;

public interface GetVehicleAssignedToRouteUseCase {
    Optional<Vehicle> execute(Long routeId);
}

