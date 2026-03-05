package com.maria.logistica.application.vehicle;

import com.maria.logistica.domain.model.Vehicle;
import com.maria.logistica.domain.port.output.VehicleRepositoryPort;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UpdateVehicleUseCaseImplTest {

    @Mock
    private VehicleRepositoryPort vehicleRepositoryPort;

    @InjectMocks
    private UpdateVehicleUseCaseImpl updateVehicleUseCase;

    @Test
    void shouldUpdateVehicle() {

        Vehicle existing = new Vehicle();
        existing.setId(1L);

        Vehicle updated = new Vehicle();
        updated.setLicensePlate("NEW123");

        when(vehicleRepositoryPort.findByLicensePlate("ABC"))
                .thenReturn(Optional.of(existing));

        when(vehicleRepositoryPort.save(updated))
                .thenReturn(updated);

        Optional<Vehicle> result =
                updateVehicleUseCase.execute("ABC", updated);

        assertTrue(result.isPresent());

        verify(vehicleRepositoryPort).findByLicensePlate("ABC");
        verify(vehicleRepositoryPort).save(updated);
    }
}