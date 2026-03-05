package com.maria.logistica.domain.port.input;

import com.maria.logistica.domain.model.Route;
import java.util.Optional;

public interface AssignVehicleToRouteUseCase {
    Optional<Route> execute(Long routeId, String vehicleLicensePlate);
}

