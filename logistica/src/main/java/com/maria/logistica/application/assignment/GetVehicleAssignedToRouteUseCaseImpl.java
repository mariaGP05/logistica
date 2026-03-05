package com.maria.logistica.application.assignment;

import com.maria.logistica.domain.model.Vehicle;
import com.maria.logistica.domain.port.input.GetVehicleAssignedToRouteUseCase;
import com.maria.logistica.domain.port.output.RouteRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetVehicleAssignedToRouteUseCaseImpl implements GetVehicleAssignedToRouteUseCase {

    private final RouteRepositoryPort routeRepositoryPort;

    @Override
    public Optional<Vehicle> execute(Long routeId) {

        Optional<Vehicle> vehicle = routeRepositoryPort.findById(routeId)
                .flatMap(route -> Optional.ofNullable(route.getVehicleAssigned()));

        if (vehicle.isPresent()) {
            log.info("Vehículo encontrado para la ruta {}", routeId);
        } else {
            log.error("No se encontró vehículo asignado para la ruta {}", routeId);
        }

        return vehicle;
    }
}


