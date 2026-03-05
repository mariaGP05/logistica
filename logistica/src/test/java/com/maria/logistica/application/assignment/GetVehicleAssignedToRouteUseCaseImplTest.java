package com.maria.logistica.application.assignment;

import com.maria.logistica.domain.model.Route;
import com.maria.logistica.domain.model.Vehicle;
import com.maria.logistica.domain.port.output.RouteRepositoryPort;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class GetVehicleAssignedToRouteUseCaseImplTest {

    @Mock
    private RouteRepositoryPort routeRepositoryPort;

    @InjectMocks
    private GetVehicleAssignedToRouteUseCaseImpl getVehicleAssignedToRouteUseCase;

    @Test
    void shouldReturnVehicle() {

        Route route = new Route();
        route.setVehicleAssigned(new Vehicle());

        when(routeRepositoryPort.findById(1L))
                .thenReturn(Optional.of(route));

        var result = getVehicleAssignedToRouteUseCase.execute(1L);

        assertTrue(result.isPresent());

        verify(routeRepositoryPort, times(1)).findById(1L);
    }
}