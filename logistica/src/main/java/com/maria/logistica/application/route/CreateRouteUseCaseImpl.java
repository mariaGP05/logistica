package com.maria.logistica.application.route;

import com.maria.logistica.domain.model.Route;
import com.maria.logistica.domain.port.input.CreateRouteUseCase;
import com.maria.logistica.domain.port.output.RouteRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateRouteUseCaseImpl implements CreateRouteUseCase {

    private final RouteRepositoryPort routeRepositoryPort;

    @Override
    public Route execute(Route route) {

        Route saved = routeRepositoryPort.save(route);

        log.info("Ruta creada con éxito. ID: {}", saved.getId());
        return saved;
    }
}


