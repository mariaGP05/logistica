package com.maria.logistica.application.assignment;

import com.maria.logistica.domain.model.Route;
import com.maria.logistica.domain.port.input.GetRoutesByVehicleUseCase;
import com.maria.logistica.domain.port.output.RouteRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetRoutesByVehicleUseCaseImpl implements GetRoutesByVehicleUseCase {

    private final RouteRepositoryPort routeRepositoryPort;

    @Override
    public List<Route> execute(String vehicleLicensePlate) {

        List<Route> routes = routeRepositoryPort.findByVehicleLicensePlate(vehicleLicensePlate);

        log.info("Número de rutas encontradas para el vehículo {}: {}", vehicleLicensePlate, routes.size());

        return routes;
    }
}


