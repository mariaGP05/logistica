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
public class GetVehicleByLicensePlateUseCaseImplTest {

    @Mock
    private VehicleRepositoryPort vehicleRepositoryPort;

    @InjectMocks
    private GetVehicleByLicensePlateUseCaseImpl getVehicleUseCase;

    @Test
    void shouldReturnVehicleByLicensePlate() {

        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate("1234ABC");

        when(vehicleRepositoryPort.findByLicensePlate("1234ABC"))
                .thenReturn(Optional.of(vehicle));

        Optional<Vehicle> result =
                getVehicleUseCase.execute("1234ABC");

        assertTrue(result.isPresent());
        assertEquals("1234ABC", result.get().getLicensePlate());

        verify(vehicleRepositoryPort, times(1))
                .findByLicensePlate("1234ABC");
    }

    @Test
    void shouldReturnEmptyWhenVehicleNotFound() {

        when(vehicleRepositoryPort.findByLicensePlate("1234ABC"))
                .thenReturn(Optional.empty());

        Optional<Vehicle> result =
                getVehicleUseCase.execute("1234ABC");

        assertTrue(result.isEmpty());

        verify(vehicleRepositoryPort).findByLicensePlate("1234ABC");
    }
}