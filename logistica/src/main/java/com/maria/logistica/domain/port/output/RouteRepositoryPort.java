package com.maria.logistica.domain.port.output;

import com.maria.logistica.domain.model.Route;
import java.util.List;
import java.util.Optional;

public interface RouteRepositoryPort {
    List<Route> findAll();
    Optional<Route> findById(Long id);
    Route save(Route route);
    void deleteById(Long id);
    List<Route> findByVehicleLicensePlate(String licensePlate);
}

