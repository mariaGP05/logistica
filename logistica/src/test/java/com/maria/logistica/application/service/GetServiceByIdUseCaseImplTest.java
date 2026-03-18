package com.maria.logistica.application.service;

import com.maria.logistica.domain.model.LogisticService;
import com.maria.logistica.domain.port.output.LogisticServiceRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetServiceByIdUseCaseImplTest {

    @Mock
    private LogisticServiceRepositoryPort logisticServiceRepositoryPort;

    @InjectMocks
    private GetServiceByIdUseCaseImpl getServiceByIdUseCase;

    @Test
    void shouldReturnServiceById() {
        LogisticService service = LogisticService.builder().id(1L).name("Express").build();

        when(logisticServiceRepositoryPort.findById(1L)).thenReturn(Optional.of(service));

        Optional<LogisticService> result = getServiceByIdUseCase.execute(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void shouldReturnEmptyWhenServiceNotFound() {
        when(logisticServiceRepositoryPort.findById(1L)).thenReturn(Optional.empty());

        Optional<LogisticService> result = getServiceByIdUseCase.execute(1L);

        assertTrue(result.isEmpty());
    }
}