package com.maria.logistica.domain.port.input;

import com.maria.logistica.domain.model.Route;
import java.util.Optional;

public interface GetRouteByIdUseCase {
    Optional<Route> execute(Long id);
}

