package com.maria.logistica.application.route;

import com.maria.logistica.domain.model.Route;
import com.maria.logistica.domain.port.output.RouteRepositoryPort;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UpdateRouteUseCaseImplTest {

    @Mock
    private RouteRepositoryPort routeRepositoryPort;

    @InjectMocks
    private UpdateRouteUseCaseImpl updateRouteUseCase;

    @Test
    void shouldUpdateRoute() {

        Route route = new Route();
        route.setId(1L);

        when(routeRepositoryPort.findById(1L))
                .thenReturn(Optional.of(route));

        when(routeRepositoryPort.save(route))
                .thenReturn(route);

        var result = updateRouteUseCase.execute(1L, route);

        assertTrue(result.isPresent());

        verify(routeRepositoryPort, times(1)).findById(1L);
        verify(routeRepositoryPort, times(1)).save(route);
    }
}