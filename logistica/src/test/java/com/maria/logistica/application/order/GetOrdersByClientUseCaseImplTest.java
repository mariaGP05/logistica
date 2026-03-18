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
class GetOrdersByClientUseCaseImplTest {

    @Mock
    private OrderRepositoryPort orderRepositoryPort;

    @InjectMocks
    private GetOrdersByClientUseCaseImpl getOrdersByClientUseCase;

    @Test
    void shouldReturnOrdersSortedByCreatedAtDesc() {
        Order oldOrder = Order.builder().id(1L).createdAt(LocalDateTime.now().minusDays(2)).build();
        Order newOrder = Order.builder().id(2L).createdAt(LocalDateTime.now()).build();

        when(orderRepositoryPort.findByClientId(1L)).thenReturn(List.of(oldOrder, newOrder));

        List<Order> result = getOrdersByClientUseCase.execute(1L);

        assertEquals(2, result.size());
        assertEquals(2L, result.get(0).getId());
        assertEquals(1L, result.get(1).getId());
    }
}