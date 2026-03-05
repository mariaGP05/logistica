package com.maria.logistica.application.route;

import com.maria.logistica.domain.model.Route;
import com.maria.logistica.domain.port.input.GetRouteByIdUseCase;
import com.maria.logistica.domain.port.output.RouteRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetRouteByIdUseCaseImpl implements GetRouteByIdUseCase {

    private final RouteRepositoryPort routeRepositoryPort;

    @Override
    public Optional<Route> execute(Long id) {

        Optional<Route> route = routeRepositoryPort.findById(id);

        if (route.isPresent()) {
            log.info("Ruta encontrada con ID: {}", id);
        } else {
            log.error("No se encontró ruta con ID: {}", id);
        }

        return route;
    }
}

