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
public class GetRouteByIdUseCaseImplTest {

    @Mock
    private RouteRepositoryPort routeRepositoryPort;

    @InjectMocks
    private GetRouteByIdUseCaseImpl getRouteByIdUseCase;

    @Test
    void shouldReturnRouteById() {

        Route route = new Route();
        route.setId(1L);

        when(routeRepositoryPort.findById(1L))
                .thenReturn(Optional.of(route));

        Optional<Route> result = getRouteByIdUseCase.execute(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());

        verify(routeRepositoryPort, times(1)).findById(1L);
    }
}