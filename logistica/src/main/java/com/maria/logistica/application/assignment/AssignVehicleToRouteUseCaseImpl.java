package com.maria.logistica.application.assignment;

import com.maria.logistica.domain.model.Route;
import com.maria.logistica.domain.model.Vehicle;
import com.maria.logistica.domain.port.input.AssignVehicleToRouteUseCase;
import com.maria.logistica.domain.port.output.RouteRepositoryPort;
import com.maria.logistica.domain.port.output.VehicleRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AssignVehicleToRouteUseCaseImpl implements AssignVehicleToRouteUseCase {

    private final RouteRepositoryPort routeRepositoryPort;
    private final VehicleRepositoryPort vehicleRepositoryPort;

    @Override
    public Optional<Route> execute(Long routeId, String vehicleLicensePlate) {

        Optional<Route> route = routeRepositoryPort.findById(routeId);
        Optional<Vehicle> vehicle = vehicleRepositoryPort.findByLicensePlate(vehicleLicensePlate);

        if (route.isPresent() && vehicle.isPresent()) {
            route.get().setVehicleAssigned(vehicle.get());
            Route saved = routeRepositoryPort.save(route.get());

            log.info("Vehículo {} asignado correctamente a la ruta {}", vehicleLicensePlate, routeId);

            return Optional.of(saved);
        }

        log.error("No se pudo asignar el vehículo {} a la ruta {}", vehicleLicensePlate, routeId);
        return Optional.empty();
    }
}


