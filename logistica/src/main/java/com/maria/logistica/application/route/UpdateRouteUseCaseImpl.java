package com.maria.logistica.application.route;

import com.maria.logistica.domain.model.Route;
import com.maria.logistica.domain.port.input.UpdateRouteUseCase;
import com.maria.logistica.domain.port.output.RouteRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateRouteUseCaseImpl implements UpdateRouteUseCase {

    private final RouteRepositoryPort routeRepositoryPort;

    @Override
    public Optional<Route> execute(Long id, Route route) {

        Optional<Route> existing = routeRepositoryPort.findById(id);

        if (existing.isPresent()) {

            route.setId(id);
            Route updated = routeRepositoryPort.save(route);

            log.info("Ruta actualizada correctamente. ID: {}", id);

            return Optional.of(updated);
        }

        log.error("No se pudo actualizar. Ruta no encontrada con ID: {}", id);
        return Optional.empty();
    }
}
