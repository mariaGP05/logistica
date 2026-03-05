package com.maria.logistica.application.route;

import com.maria.logistica.domain.port.output.RouteRepositoryPort;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DeleteRouteUseCaseImplTest {

    @Mock
    private RouteRepositoryPort routeRepositoryPort;

    @InjectMocks
    private DeleteRouteUseCaseImpl deleteRouteUseCase;

    @Test
    void shouldDeleteRoute() {

        when(routeRepositoryPort.findById(1L))
                .thenReturn(java.util.Optional.of(new com.maria.logistica.domain.model.Route()));

        deleteRouteUseCase.execute(1L);

        verify(routeRepositoryPort, times(1)).deleteById(1L);
    }
}