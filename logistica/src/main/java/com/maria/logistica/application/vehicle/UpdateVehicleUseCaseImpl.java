package com.maria.logistica.application.vehicle;

import com.maria.logistica.domain.model.Vehicle;
import com.maria.logistica.domain.port.input.UpdateVehicleUseCase;
import com.maria.logistica.domain.port.output.VehicleRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateVehicleUseCaseImpl implements UpdateVehicleUseCase {

    private final VehicleRepositoryPort vehicleRepositoryPort;

    @Override
    public Optional<Vehicle> execute(String licensePlate, Vehicle vehicle) {

        Optional<Vehicle> existing = vehicleRepositoryPort.findByLicensePlate(licensePlate);

        if (existing.isPresent()) {

            vehicle.setId(existing.get().getId());
            Vehicle updated = vehicleRepositoryPort.save(vehicle);

            log.info("Vehículo actualizado correctamente. Matrícula: {}", licensePlate);

            return Optional.of(updated);
        }

        log.error("No se pudo actualizar. Vehículo no encontrado con matrícula: {}", licensePlate);

        return Optional.empty();
    }
}

