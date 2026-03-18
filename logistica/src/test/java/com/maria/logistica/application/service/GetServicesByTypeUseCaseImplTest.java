package com.maria.logistica.application.service;

import com.maria.logistica.domain.model.LogisticService;
import com.maria.logistica.domain.model.ServiceType;
import com.maria.logistica.domain.port.output.LogisticServiceRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetServicesByTypeUseCaseImplTest {

    @Mock
    private LogisticServiceRepositoryPort logisticServiceRepositoryPort;

    @InjectMocks
    private GetServicesByTypeUseCaseImpl getServicesByTypeUseCase;

    @Test
    void shouldReturnOnlyAvailableServicesByType() {
        LogisticService s1 = LogisticService.builder().id(1L).type(ServiceType.EXPRESS).pricePerKm(new BigDecimal("0.50")).available(true).build();
        LogisticService s2 = LogisticService.builder().id(2L).type(ServiceType.EXPRESS).pricePerKm(new BigDecimal("0.70")).available(false).build();
        LogisticService s3 = LogisticService.builder().id(3L).type(ServiceType.STANDARD).pricePerKm(new BigDecimal("0.60")).available(true).build();

        when(logisticServiceRepositoryPort.findAll()).thenReturn(List.of(s1, s2, s3));

        List<LogisticService> result = getServicesByTypeUseCase.execute(ServiceType.EXPRESS);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }
}