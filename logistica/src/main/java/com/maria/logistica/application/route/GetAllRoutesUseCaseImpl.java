package com.maria.logistica.application.route;

import com.maria.logistica.domain.model.Route;
import com.maria.logistica.domain.port.input.GetAllRoutesUseCase;
import com.maria.logistica.domain.port.output.RouteRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetAllRoutesUseCaseImpl implements GetAllRoutesUseCase {

    private final RouteRepositoryPort routeRepositoryPort;

    @Override
    public List<Route> execute() {
        List<Route> routes = routeRepositoryPort.findAll();
        log.info("Número de rutas encontradas: {}", routes.size());
        return routes;
    }
}

