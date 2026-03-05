package com.maria.logistica.domain.port.input;

import com.maria.logistica.domain.model.Route;
import java.util.List;

public interface GetAllRoutesUseCase {
    List<Route> execute();
}

