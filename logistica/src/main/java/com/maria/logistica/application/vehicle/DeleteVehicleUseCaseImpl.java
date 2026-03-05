package com.maria.logistica.application.vehicle;

import com.maria.logistica.domain.port.input.DeleteVehicleUseCase;
import com.maria.logistica.domain.port.output.VehicleRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteVehicleUseCaseImpl implements DeleteVehicleUseCase {

    private final VehicleRepositoryPort vehicleRepositoryPort;

    @Override
    public boolean execute(String licensePlate) {

        if (vehicleRepositoryPort.findByLicensePlate(licensePlate).isPresent()) {
            vehicleRepositoryPort.deleteByLicensePlate(licensePlate);

            log.info("Vehículo eliminado correctamente. Matrícula: {}", licensePlate);

            return true;
        }

        log.error("No se pudo eliminar el vehículo. Matrícula no encontrada: {}", licensePlate);

        return false;
    }
}


