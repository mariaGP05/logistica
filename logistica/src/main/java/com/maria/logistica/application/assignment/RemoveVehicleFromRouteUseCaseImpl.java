package com.maria.logistica.application.assignment;

import com.maria.logistica.domain.port.input.RemoveVehicleFromRouteUseCase;
import com.maria.logistica.domain.port.output.RouteRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RemoveVehicleFromRouteUseCaseImpl implements RemoveVehicleFromRouteUseCase {

    private final RouteRepositoryPort routeRepositoryPort;

    @Override
    public boolean execute(Long routeId) {

        var route = routeRepositoryPort.findById(routeId);

        if (route.isPresent() && route.get().getVehicleAssigned() != null) {

            route.get().setVehicleAssigned(null);
            routeRepositoryPort.save(route.get());

            log.info("Vehículo eliminado correctamente de la ruta {}", routeId);

            return true;
        }

        log.error("No se pudo eliminar el vehículo de la ruta {}", routeId);
        return false;
    }
}

