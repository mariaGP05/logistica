package com.maria.logistica.application.vehicle;

import com.maria.logistica.domain.model.Vehicle;
import com.maria.logistica.domain.port.input.CreateVehicleUseCase;
import com.maria.logistica.domain.port.output.VehicleRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateVehicleUseCaseImpl implements CreateVehicleUseCase {

    private final VehicleRepositoryPort vehicleRepositoryPort;

    @Override
    public Vehicle execute(Vehicle vehicle) {
        log.info("Registrando vehículo con matrícula: {}", vehicle.getLicensePlate());

        Vehicle saved = vehicleRepositoryPort.save(vehicle);

        log.info("Vehículo registrado exitosamente.");
        return saved;
    }
}


