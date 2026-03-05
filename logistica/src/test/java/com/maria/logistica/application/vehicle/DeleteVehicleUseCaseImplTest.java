package com.maria.logistica.application.vehicle;

import com.maria.logistica.domain.model.Vehicle;
import com.maria.logistica.domain.port.output.VehicleRepositoryPort;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class DeleteVehicleUseCaseImplTest {

    @Mock
    private VehicleRepositoryPort vehicleRepositoryPort;

    @InjectMocks
    private DeleteVehicleUseCaseImpl deleteVehicleUseCase;

    @Test
    void shouldDeleteVehicle() {

        when(vehicleRepositoryPort.findByLicensePlate("1234ABC"))
                .thenReturn(Optional.of(new Vehicle()));

        deleteVehicleUseCase.execute("1234ABC");

        verify(vehicleRepositoryPort, times(1))
                .deleteByLicensePlate("1234ABC");
    }
}