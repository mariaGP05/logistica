package com.maria.logistica.domain.port.input;

import com.maria.logistica.domain.model.Vehicle;
import java.util.List;

public interface GetAllVehiclesUseCase {
    List<Vehicle> execute();
}

