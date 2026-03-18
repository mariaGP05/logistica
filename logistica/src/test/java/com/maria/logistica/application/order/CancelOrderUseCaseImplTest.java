package com.maria.logistica.application.order;

import com.maria.logistica.domain.model.Order;
import com.maria.logistica.domain.model.OrderStatus;
import com.maria.logistica.domain.port.output.OrderRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CancelOrderUseCaseImplTest {

    @Mock
    private OrderRepositoryPort orderRepositoryPort;

    @InjectMocks
    private CancelOrderUseCaseImpl cancelOrderUseCase;

    @Test
    void shouldCancelOrder() {
        Order order = Order.builder().id(1L).status(OrderStatus.PENDING).build();

        when(orderRepositoryPort.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepositoryPort.save(order)).thenReturn(order);

        boolean result = cancelOrderUseCase.execute(1L);

        assertTrue(result);
        assertEquals(OrderStatus.CANCELLED, order.getStatus());

        verify(orderRepositoryPort).findById(1L);
        verify(orderRepositoryPort).save(order);
    }

    @Test
    void shouldReturnFalseWhenOrderNotFound() {
        when(orderRepositoryPort.findById(1L)).thenReturn(Optional.empty());

        boolean result = cancelOrderUseCase.execute(1L);

        assertFalse(result);

        verify(orderRepositoryPort).findById(1L);
        verify(orderRepositoryPort, never()).save(any());
    }
}