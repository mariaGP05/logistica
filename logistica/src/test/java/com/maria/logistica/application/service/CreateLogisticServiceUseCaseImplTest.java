package com.maria.logistica.application.service;

import com.maria.logistica.domain.model.LogisticService;
import com.maria.logistica.domain.model.ServiceType;
import com.maria.logistica.domain.port.output.LogisticServiceRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateLogisticServiceUseCaseImplTest {

    @Mock
    private LogisticServiceRepositoryPort logisticServiceRepositoryPort;

    @InjectMocks
    private CreateLogisticServiceUseCaseImpl createLogisticServiceUseCase;

    @Test
    void shouldCreateLogisticService() {
        LogisticService service = LogisticService.builder()
                .name("Express")
                .type(ServiceType.EXPRESS)
                .pricePerKm(new BigDecimal("0.50"))
                .description("Urgente")
                .build();

        LogisticService saved = LogisticService.builder()
                .id(1L)
                .name("Express")
                .type(ServiceType.EXPRESS)
                .pricePerKm(new BigDecimal("0.50"))
                .description("Urgente")
                .available(true)
                .build();

        when(logisticServiceRepositoryPort.save(any(LogisticService.class))).thenReturn(saved);

        LogisticService result = createLogisticServiceUseCase.execute(service);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertTrue(result.getAvailable());

        verify(logisticServiceRepositoryPort).save(service);
    }
}