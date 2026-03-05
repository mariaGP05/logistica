package com.maria.logistica.application.assignment;

import com.maria.logistica.domain.model.Route;
import com.maria.logistica.domain.port.output.RouteRepositoryPort;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class GetRoutesByVehicleUseCaseImplTest {

    @Mock
    private RouteRepositoryPort routeRepositoryPort;

    @InjectMocks
    private GetRoutesByVehicleUseCaseImpl getRoutesByVehicleUseCase;

    @Test
    void shouldReturnRoutes() {

        when(routeRepositoryPort.findByVehicleLicensePlate("ABC123"))
                .thenReturn(List.of(new Route()));

        var result = getRoutesByVehicleUseCase.execute("ABC123");

        assertEquals(1, result.size());

        verify(routeRepositoryPort, times(1))
                .findByVehicleLicensePlate("ABC123");
    }
}