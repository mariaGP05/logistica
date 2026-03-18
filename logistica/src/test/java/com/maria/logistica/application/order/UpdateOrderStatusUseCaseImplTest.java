package com.maria.logistica.application.order;

import com.maria.logistica.domain.model.Order;
import com.maria.logistica.domain.model.OrderStatus;
import com.maria.logistica.domain.port.output.OrderRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDateTime;
import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateOrderStatusUseCaseImplTest {

    @Mock
    private OrderRepositoryPort orderRepositoryPort;

    @InjectMocks
    private UpdateOrderStatusUseCaseImpl updateOrderStatusUseCase;

    @Test
    void shouldUpdateOrderStatus() {
        Order order = Order.builder().id(1L).status(OrderStatus.PENDING).build();

        when(orderRepositoryPort.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepositoryPort.save(order)).thenReturn(order);

        Optional<Order> result = updateOrderStatusUseCase.execute(1L, OrderStatus.CONFIRMED);

        assertTrue(result.isPresent());
        assertEquals(OrderStatus.CONFIRMED, result.get().getStatus());

        verify(orderRepositoryPort).findById(1L);
        verify(orderRepositoryPort).save(order);
    }

    @Test
    void shouldSetDeliveredAtWhenStatusIsDelivered() {
        Order order = Order.builder().id(1L).status(OrderStatus.IN_TRANSIT).build();

        when(orderRepositoryPort.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepositoryPort.save(order)).thenReturn(order);

        Optional<Order> result = updateOrderStatusUseCase.execute(1L, OrderStatus.DELIVERED);

        assertTrue(result.isPresent());
        assertEquals(OrderStatus.DELIVERED, result.get().getStatus());
        assertNotNull(result.get().getDeliveredAt());
    }

    @Test
    void shouldReturnEmptyWhenOrderNotFound() {
        when(orderRepositoryPort.findById(1L)).thenReturn(Optional.empty());

        Optional<Order> result = updateOrderStatusUseCase.execute(1L, OrderStatus.CONFIRMED);

        assertTrue(result.isEmpty());

        verify(orderRepositoryPort).findById(1L);
        verify(orderRepositoryPort, never()).save(any());
    }
}