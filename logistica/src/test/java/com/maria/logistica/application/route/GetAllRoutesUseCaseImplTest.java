package com.maria.logistica.application.route;

import com.maria.logistica.domain.model.Route;
import com.maria.logistica.domain.port.output.RouteRepositoryPort;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GetAllRoutesUseCaseImplTest {

    @Mock
    private RouteRepositoryPort routeRepositoryPort;

    @InjectMocks
    private GetAllRoutesUseCaseImpl getAllRoutesUseCase;

    @Test
    void shouldReturnAllRoutes() {

        when(routeRepositoryPort.findAll())
                .thenReturn(List.of(new Route()));

        var result = getAllRoutesUseCase.execute();

        assertEquals(1, result.size());

        verify(routeRepositoryPort, times(1)).findAll();
    }

    @Test
    void shouldReturnEmptyList() {

        when(routeRepositoryPort.findAll())
                .thenReturn(List.of());

        var result = getAllRoutesUseCase.execute();

        assertTrue(result.isEmpty());

        verify(routeRepositoryPort).findAll();
    }
}