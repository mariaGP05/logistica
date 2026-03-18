package com.maria.logistica.application.order;

import com.maria.logistica.domain.model.Order;
import com.maria.logistica.domain.model.OrderStatus;
import com.maria.logistica.domain.port.output.OrderRepositoryPort;
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
class GetTotalSpentByClientUseCaseImplTest {

    @Mock
    private OrderRepositoryPort orderRepositoryPort;

    @InjectMocks
    private GetTotalSpentByClientUseCaseImpl getTotalSpentByClientUseCase;

    @Test
    void shouldCalculateTotalSpentExcludingCancelledOrders() {
        Order delivered = Order.builder()
                .id(1L)
                .status(OrderStatus.DELIVERED)
                .totalCost(new BigDecimal("100.00"))
                .build();

        Order pending = Order.builder()
                .id(2L)
                .status(OrderStatus.PENDING)
                .totalCost(new BigDecimal("50.00"))
                .build();

        Order cancelled = Order.builder()
                .id(3L)
                .status(OrderStatus.CANCELLED)
                .totalCost(new BigDecimal("999.00"))
                .build();

        when(orderRepositoryPort.findByClientId(1L)).thenReturn(List.of(delivered, pending, cancelled));

        BigDecimal result = getTotalSpentByClientUseCase.execute(1L);

        assertEquals(new BigDecimal("150.00"), result);
    }

    @Test
    void shouldTreatNullTotalCostAsZero() {
        Order deliveredWithNullCost = Order.builder()
                .id(1L)
                .status(OrderStatus.DELIVERED)
                .totalCost(null)
                .build();

        Order pending = Order.builder()
                .id(2L)
                .status(OrderStatus.PENDING)
                .totalCost(new BigDecimal("40.00"))
                .build();

        Order cancelledWithNullCost = Order.builder()
                .id(3L)
                .status(OrderStatus.CANCELLED)
                .totalCost(null)
                .build();

        when(orderRepositoryPort.findByClientId(1L)).thenReturn(List.of(
                deliveredWithNullCost,
                pending,
                cancelledWithNullCost
        ));

        BigDecimal result = getTotalSpentByClientUseCase.execute(1L);

        assertEquals(new BigDecimal("40.00"), result);
    }
}