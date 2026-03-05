package com.maria.logistica.application.vehicle;

import com.maria.logistica.domain.model.Vehicle;
import com.maria.logistica.domain.port.output.VehicleRepositoryPort;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GetAllVehiclesUseCaseImplTest {

    @Mock
    private VehicleRepositoryPort vehicleRepositoryPort;

    @InjectMocks
    private GetAllVehiclesUseCaseImpl getAllVehiclesUseCase;

    @Test
    void shouldReturnAllVehicles() {

        when(vehicleRepositoryPort.findAll())
                .thenReturn(List.of(new Vehicle()));

        var result = getAllVehiclesUseCase.execute();

        assertEquals(1, result.size());

        verify(vehicleRepositoryPort, times(1)).findAll();
    }
}