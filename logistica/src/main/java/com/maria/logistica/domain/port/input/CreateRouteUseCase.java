package com.maria.logistica.domain.port.input;

import com.maria.logistica.domain.model.Route;

public interface CreateRouteUseCase {
    Route execute(Route route);
}

