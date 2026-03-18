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
class GetAllServicesUseCaseImplTest {

    @Mock
    private LogisticServiceRepositoryPort logisticServiceRepositoryPort;

    @InjectMocks
    private GetAllServicesUseCaseImpl getAllServicesUseCase;

    @Test
    void shouldReturnOnlyAvailableServicesSortedByName() {
        LogisticService s1 = LogisticService.builder().id(1L).name("Zeta").type(ServiceType.STANDARD).pricePerKm(new BigDecimal("0.50")).available(true).build();
        LogisticService s2 = LogisticService.builder().id(2L).name("Alpha").type(ServiceType.EXPRESS).pricePerKm(new BigDecimal("0.70")).available(true).build();
        LogisticService s3 = LogisticService.builder().id(3L).name("Beta").type(ServiceType.HEAVY).pricePerKm(new BigDecimal("0.90")).available(false).build();

        when(logisticServiceRepositoryPort.findAll()).thenReturn(List.of(s1, s2, s3));

        List<LogisticService> result = getAllServicesUseCase.execute();

        assertEquals(2, result.size());
        assertEquals("Alpha", result.get(0).getName());
        assertEquals("Zeta", result.get(1).getName());
    }
}