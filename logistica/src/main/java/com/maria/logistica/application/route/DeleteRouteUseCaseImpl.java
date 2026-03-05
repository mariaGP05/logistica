package com.maria.logistica.application.route;

import com.maria.logistica.domain.port.input.DeleteRouteUseCase;
import com.maria.logistica.domain.port.output.RouteRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteRouteUseCaseImpl implements DeleteRouteUseCase {

    private final RouteRepositoryPort routeRepositoryPort;

    @Override
    public boolean execute(Long id) {

        if (routeRepositoryPort.findById(id).isPresent()) {
            routeRepositoryPort.deleteById(id);
            log.info("Ruta eliminada correctamente. ID: {}", id);
            return true;
        }

        log.error("No se pudo eliminar la ruta. No existe ruta con ID: {}", id);
        return false;
    }
}

