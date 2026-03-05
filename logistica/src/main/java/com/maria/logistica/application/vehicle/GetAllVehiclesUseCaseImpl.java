package com.maria.logistica.application.vehicle;

import com.maria.logistica.domain.model.Vehicle;
import com.maria.logistica.domain.port.input.GetAllVehiclesUseCase;
import com.maria.logistica.domain.port.output.VehicleRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetAllVehiclesUseCaseImpl implements GetAllVehiclesUseCase {

    private final VehicleRepositoryPort vehicleRepositoryPort;

    @Override
    public List<Vehicle> execute() {

        List<Vehicle> vehicles = vehicleRepositoryPort.findAll();

        log.info("Número de vehículos encontrados: {}", vehicles.size());

        return vehicles;
    }
}


