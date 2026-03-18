package com.maria.logistica.application.order;

import com.maria.logistica.domain.model.Order;
import com.maria.logistica.domain.port.output.OrderRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDateTime;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetOrdersByDateRangeUseCaseImplTest {

    @Mock
    private OrderRepositoryPort orderRepositoryPort;

    @InjectMocks
    private GetOrdersByDateRangeUseCaseImpl getOrdersByDateRangeUseCase;

    @Test
    void shouldReturnOrdersWithinDateRangeSortedDesc() {
        LocalDateTime from = LocalDateTime.of(2026, 1, 1, 0, 0);
        LocalDateTime to = LocalDateTime.of(2026, 12, 31, 23, 59);

        Order insideOld = Order.builder().id(1L).createdAt(LocalDateTime.of(2026, 2, 1, 10, 0)).build();
        Order insideNew = Order.builder().id(2L).createdAt(LocalDateTime.of(2026, 3, 1, 10, 0)).build();
        Order outside = Order.builder().id(3L).createdAt(LocalDateTime.of(2025, 12, 31, 10, 0)).build();

        when(orderRepositoryPort.findAll()).thenReturn(List.of(insideOld, insideNew, outside));

        List<Order> result = getOrdersByDateRangeUseCase.execute(from, to);

        assertEquals(2, result.size());
        assertEquals(2L, result.get(0).getId());
        assertEquals(1L, result.get(1).getId());
    }
}