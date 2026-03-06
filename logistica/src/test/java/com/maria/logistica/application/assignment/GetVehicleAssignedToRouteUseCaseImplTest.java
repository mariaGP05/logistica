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
class GetVehicleAssignedToRouteUseCaseImplTest {

    @Mock
    private RouteRepositoryPort routeRepositoryPort;

    @InjectMocks
    private GetVehicleAssignedToRouteUseCaseImpl getVehicleAssignedToRouteUseCase;

    @Test
    void shouldReturnVehicle() {

        Vehicle vehicle = new Vehicle();
        Route route = new Route();
        route.setVehicleAssigned(vehicle);

        when(routeRepositoryPort.findById(1L))
                .thenReturn(Optional.of(route));

        Optional<Vehicle> result = getVehicleAssignedToRouteUseCase.execute(1L);

        assertTrue(result.isPresent());
        assertEquals(vehicle, result.get());

        verify(routeRepositoryPort).findById(1L);
    }

    @Test
    void shouldReturnEmptyWhenRouteHasNoVehicle() {

        Route route = new Route(); // sin vehículo asignado

        when(routeRepositoryPort.findById(1L))
                .thenReturn(Optional.of(route));

        Optional<Vehicle> result = getVehicleAssignedToRouteUseCase.execute(1L);

        assertTrue(result.isEmpty());

        verify(routeRepositoryPort).findById(1L);
    }

    @Test
    void shouldReturnEmptyWhenRouteNotFound() {

        when(routeRepositoryPort.findById(1L))
                .thenReturn(Optional.empty());

        Optional<Vehicle> result = getVehicleAssignedToRouteUseCase.execute(1L);

        assertTrue(result.isEmpty());

        verify(routeRepositoryPort).findById(1L);
    }
}