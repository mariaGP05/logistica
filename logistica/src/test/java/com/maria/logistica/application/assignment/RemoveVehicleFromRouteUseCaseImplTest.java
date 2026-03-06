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
class RemoveVehicleFromRouteUseCaseImplTest {

    @Mock
    private RouteRepositoryPort routeRepositoryPort;

    @InjectMocks
    private RemoveVehicleFromRouteUseCaseImpl removeVehicleFromRouteUseCase;

    @Test
    void shouldRemoveVehicle() {

        Route route = new Route();
        route.setVehicleAssigned(new Vehicle());

        when(routeRepositoryPort.findById(1L))
                .thenReturn(Optional.of(route));

        when(routeRepositoryPort.save(route))
                .thenReturn(route);

        boolean result = removeVehicleFromRouteUseCase.execute(1L);

        assertTrue(result);
        assertNull(route.getVehicleAssigned());

        verify(routeRepositoryPort).save(route);
    }

    @Test
    void shouldReturnFalseWhenRouteHasNoVehicle() {

        Route route = new Route(); // sin vehículo

        when(routeRepositoryPort.findById(1L))
                .thenReturn(Optional.of(route));

        boolean result = removeVehicleFromRouteUseCase.execute(1L);

        assertFalse(result);

        verify(routeRepositoryPort, never()).save(any());
    }

    @Test
    void shouldReturnFalseWhenRouteNotFound() {

        when(routeRepositoryPort.findById(1L))
                .thenReturn(Optional.empty());

        boolean result = removeVehicleFromRouteUseCase.execute(1L);

        assertFalse(result);

        verify(routeRepositoryPort, never()).save(any());
    }
}