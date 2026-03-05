package com.maria.logistica.application.vehicle;

import com.maria.logistica.domain.model.Vehicle;
import com.maria.logistica.domain.port.input.GetVehicleByLicensePlateUseCase;
import com.maria.logistica.domain.port.output.VehicleRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetVehicleByLicensePlateUseCaseImpl implements GetVehicleByLicensePlateUseCase {

    private final VehicleRepositoryPort vehicleRepositoryPort;

    @Override
    public Optional<Vehicle> execute(String licensePlate) {

        Optional<Vehicle> vehicle = vehicleRepositoryPort.findByLicensePlate(licensePlate);

        if (vehicle.isPresent()) {
            log.info("Vehículo encontrado con matrícula: {}", licensePlate);
        } else {
            log.error("No se encontró vehículo con matrícula: {}", licensePlate);
        }

        return vehicle;
    }
}

