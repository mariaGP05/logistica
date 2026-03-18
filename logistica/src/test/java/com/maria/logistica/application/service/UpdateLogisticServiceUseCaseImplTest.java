package com.maria.logistica.application.service;

import com.maria.logistica.domain.model.LogisticService;
import com.maria.logistica.domain.model.ServiceType;
import com.maria.logistica.domain.port.output.LogisticServiceRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateLogisticServiceUseCaseImplTest {

    @Mock
    private LogisticServiceRepositoryPort logisticServiceRepositoryPort;

    @InjectMocks
    private UpdateLogisticServiceUseCaseImpl updateLogisticServiceUseCase;

    @Test
    void shouldUpdateService() {
        LogisticService existing = LogisticService.builder()
                .id(1L)
                .available(true)
                .build();

        LogisticService request = LogisticService.builder()
                .name("Express")
                .type(ServiceType.EXPRESS)
                .pricePerKm(new BigDecimal("0.75"))
                .description("Urgente")
                .available(true)
                .build();

        LogisticService saved = LogisticService.builder()
                .id(1L)
                .name("Express")
                .type(ServiceType.EXPRESS)
                .pricePerKm(new BigDecimal("0.75"))
                .description("Urgente")
                .available(true)
                .build();

        when(logisticServiceRepositoryPort.findById(1L)).thenReturn(Optional.of(existing));
        when(logisticServiceRepositoryPort.save(any(LogisticService.class))).thenReturn(saved);

        Optional<LogisticService> result = updateLogisticServiceUseCase.execute(1L, request);

        assertTrue(result.isPresent());
        assertEquals(new BigDecimal("0.75"), result.get().getPricePerKm());
        assertEquals(1L, request.getId());
        assertTrue(request.getAvailable());

        verify(logisticServiceRepositoryPort).findById(1L);
        verify(logisticServiceRepositoryPort).save(any(LogisticService.class));
    }

    @Test
    void shouldKeepExistingAvailabilityWhenRequestAvailabilityIsNull() {
        LogisticService existing = LogisticService.builder()
                .id(1L)
                .available(false)
                .build();

        LogisticService request = LogisticService.builder()
                .name("Express")
                .type(ServiceType.EXPRESS)
                .pricePerKm(new BigDecimal("0.75"))
                .description("Urgente")
                .available(null)
                .build();

        LogisticService saved = LogisticService.builder()
                .id(1L)
                .name("Express")
                .type(ServiceType.EXPRESS)
                .pricePerKm(new BigDecimal("0.75"))
                .description("Urgente")
                .available(false)
                .build();

        when(logisticServiceRepositoryPort.findById(1L)).thenReturn(Optional.of(existing));
        when(logisticServiceRepositoryPort.save(any(LogisticService.class))).thenReturn(saved);

        Optional<LogisticService> result = updateLogisticServiceUseCase.execute(1L, request);

        assertTrue(result.isPresent());
        assertEquals(1L, request.getId());
        assertFalse(request.getAvailable());
        assertFalse(result.get().getAvailable());

        verify(logisticServiceRepositoryPort).findById(1L);
        verify(logisticServiceRepositoryPort).save(any(LogisticService.class));
    }

    @Test
    void shouldReturnEmptyWhenServiceNotFound() {
        LogisticService request = LogisticService.builder()
                .name("Express")
                .build();

        when(logisticServiceRepositoryPort.findById(1L)).thenReturn(Optional.empty());

        Optional<LogisticService> result = updateLogisticServiceUseCase.execute(1L, request);

        assertTrue(result.isEmpty());

        verify(logisticServiceRepositoryPort).findById(1L);
        verify(logisticServiceRepositoryPort, never()).save(any());
    }
}