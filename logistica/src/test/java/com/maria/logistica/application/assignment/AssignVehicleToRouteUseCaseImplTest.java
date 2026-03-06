package com.maria.logistica.application.assignment;

import com.maria.logistica.domain.model.Route;
import com.maria.logistica.domain.model.Vehicle;
import com.maria.logistica.domain.port.output.RouteRepositoryPort;
import com.maria.logistica.domain.port.output.VehicleRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AssignVehicleToRouteUseCaseImplTest {

    @Mock
    private RouteRepositoryPort routeRepositoryPort;

    @Mock
    private VehicleRepositoryPort vehicleRepositoryPort;

    @InjectMocks
    private AssignVehicleToRouteUseCaseImpl assignVehicleToRouteUseCase;

    @Test
    void shouldAssignVehicleToRoute() {

        Route route = new Route();
        Vehicle vehicle = new Vehicle();

        when(routeRepositoryPort.findById(1L))
                .thenReturn(Optional.of(route));

        when(vehicleRepositoryPort.findByLicensePlate("ABC123"))
                .thenReturn(Optional.of(vehicle));

        when(routeRepositoryPort.save(route))
                .thenReturn(route);

        Optional<Route> result = assignVehicleToRouteUseCase.execute(1L, "ABC123");

        assertTrue(result.isPresent());
        assertEquals(route, result.get());

        //verificar que el vehículo se asignó
        assertEquals(vehicle, route.getVehicleAssigned());

        verify(routeRepositoryPort, times(1)).save(route);
    }

    @Test
    void shouldReturnEmptyIfRouteNotFound() {

        Vehicle vehicle = new Vehicle();

        when(routeRepositoryPort.findById(1L))
                .thenReturn(Optional.empty());

        when(vehicleRepositoryPort.findByLicensePlate("ABC123"))
                .thenReturn(Optional.of(vehicle));

        Optional<Route> result = assignVehicleToRouteUseCase.execute(1L, "ABC123");

        assertTrue(result.isEmpty());

        verify(routeRepositoryPort, never()).save(any());
    }

    @Test
    void shouldReturnEmptyIfVehicleNotFound() {

        Route route = new Route();

        when(routeRepositoryPort.findById(1L))
                .thenReturn(Optional.of(route));

        when(vehicleRepositoryPort.findByLicensePlate("ABC123"))
                .thenReturn(Optional.empty());

        Optional<Route> result = assignVehicleToRouteUseCase.execute(1L, "ABC123");

        assertTrue(result.isEmpty());

        verify(routeRepositoryPort).findById(1L);
        verify(vehicleRepositoryPort).findByLicensePlate("ABC123");
        verify(routeRepositoryPort, never()).save(any());
    }

    @Test
    void shouldReturnEmptyIfRouteAndVehicleNotFound() {

        when(routeRepositoryPort.findById(1L))
                .thenReturn(Optional.empty());

        when(vehicleRepositoryPort.findByLicensePlate("ABC123"))
                .thenReturn(Optional.empty());

        Optional<Route> result = assignVehicleToRouteUseCase.execute(1L, "ABC123");

        assertTrue(result.isEmpty());

        verify(routeRepositoryPort).findById(1L);
        verify(vehicleRepositoryPort).findByLicensePlate("ABC123");
        verify(routeRepositoryPort, never()).save(any());
    }
}