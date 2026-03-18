package com.maria.logistica.application.order;

import com.maria.logistica.domain.model.LogisticService;
import com.maria.logistica.domain.model.Order;
import com.maria.logistica.domain.model.ServiceType;
import com.maria.logistica.domain.port.output.OrderRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetOrdersByServiceTypeUseCaseImplTest {

    @Mock
    private OrderRepositoryPort orderRepositoryPort;

    @InjectMocks
    private GetOrdersByServiceTypeUseCaseImpl getOrdersByServiceTypeUseCase;

    @Test
    void shouldReturnOrdersByServiceType() {
        Order express = Order.builder()
                .id(1L)
                .service(LogisticService.builder().type(ServiceType.EXPRESS).build())
                .build();

        Order standard = Order.builder()
                .id(2L)
                .service(LogisticService.builder().type(ServiceType.STANDARD).build())
                .build();

        when(orderRepositoryPort.findAll()).thenReturn(List.of(express, standard));

        List<Order> result = getOrdersByServiceTypeUseCase.execute(ServiceType.EXPRESS);

        assertEquals(1, result.size());
        assertEquals(1L, result.getFirst().getId());
    }

    @Test
    void shouldIgnoreOrdersWithoutServiceAndOrdersWithDifferentServiceType() {
        Order withoutService = Order.builder()
                .id(1L)
                .service(null)
                .build();

        Order standard = Order.builder()
                .id(2L)
                .service(LogisticService.builder().type(ServiceType.STANDARD).build())
                .build();

        Order express = Order.builder()
                .id(3L)
                .service(LogisticService.builder().type(ServiceType.EXPRESS).build())
                .build();

        when(orderRepositoryPort.findAll()).thenReturn(List.of(withoutService, standard, express));

        List<Order> result = getOrdersByServiceTypeUseCase.execute(ServiceType.EXPRESS);

        assertEquals(1, result.size());
        assertEquals(3L, result.getFirst().getId());
    }
}