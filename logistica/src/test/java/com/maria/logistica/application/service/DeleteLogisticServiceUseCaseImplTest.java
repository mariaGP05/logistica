package com.maria.logistica.application.service;

import com.maria.logistica.domain.model.LogisticService;
import com.maria.logistica.domain.port.output.LogisticServiceRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteLogisticServiceUseCaseImplTest {

    @Mock
    private LogisticServiceRepositoryPort logisticServiceRepositoryPort;

    @InjectMocks
    private DeleteLogisticServiceUseCaseImpl deleteLogisticServiceUseCase;

    @Test
    void shouldDeleteService() {
        LogisticService service = LogisticService.builder().id(1L).build();

        when(logisticServiceRepositoryPort.findById(1L)).thenReturn(Optional.of(service));

        boolean result = deleteLogisticServiceUseCase.execute(1L);

        assertTrue(result);

        verify(logisticServiceRepositoryPort).findById(1L);
        verify(logisticServiceRepositoryPort).deleteById(1L);
    }

    @Test
    void shouldReturnFalseWhenServiceNotFound() {
        when(logisticServiceRepositoryPort.findById(1L)).thenReturn(Optional.empty());

        boolean result = deleteLogisticServiceUseCase.execute(1L);

        assertFalse(result);

        verify(logisticServiceRepositoryPort).findById(1L);
        verify(logisticServiceRepositoryPort, never()).deleteById(anyLong());
    }
}