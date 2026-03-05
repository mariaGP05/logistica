package com.maria.logistica.application.vehicle;

import com.maria.logistica.domain.model.Vehicle;
import com.maria.logistica.domain.port.output.VehicleRepositoryPort;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CreateVehicleUseCaseImplTest {

    @Mock
    private VehicleRepositoryPort vehicleRepositoryPort;

    @InjectMocks
    private CreateVehicleUseCaseImpl createVehicleUseCase;

    @Test
    void shouldCreateVehicleSuccessfully() {

        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate("1234ABC");

        when(vehicleRepositoryPort.save(vehicle)).thenReturn(vehicle);

        Vehicle result = createVehicleUseCase.execute(vehicle);

        assertNotNull(result);
        assertEquals("1234ABC", result.getLicensePlate());

        verify(vehicleRepositoryPort, times(1)).save(vehicle);
    }
}