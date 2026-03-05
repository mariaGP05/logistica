package com.maria.logistica.application.route;

import com.maria.logistica.domain.model.Route;
import com.maria.logistica.domain.port.output.RouteRepositoryPort;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CreateRouteUseCaseImplTest {

    @Mock
    private RouteRepositoryPort routeRepositoryPort;

    @InjectMocks
    private CreateRouteUseCaseImpl createRouteUseCase;

    @Test
    void shouldCreateRouteSuccessfully() {

        Route route = new Route();
        route.setOrigin("Madrid");

        when(routeRepositoryPort.save(route)).thenReturn(route);

        Route result = createRouteUseCase.execute(route);

        assertNotNull(result);

        verify(routeRepositoryPort, times(1)).save(route);
    }
}