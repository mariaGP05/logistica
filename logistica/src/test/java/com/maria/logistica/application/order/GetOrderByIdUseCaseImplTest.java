package com.maria.logistica.application.order;

import com.maria.logistica.domain.model.Order;
import com.maria.logistica.domain.port.output.OrderRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetOrderByIdUseCaseImplTest {

    @Mock
    private OrderRepositoryPort orderRepositoryPort;

    @InjectMocks
    private GetOrderByIdUseCaseImpl getOrderByIdUseCase;

    @Test
    void shouldReturnOrderById() {
        Order order = Order.builder().id(1L).build();

        when(orderRepositoryPort.findById(1L)).thenReturn(Optional.of(order));

        Optional<Order> result = getOrderByIdUseCase.execute(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void shouldReturnEmptyWhenOrderNotFound() {
        when(orderRepositoryPort.findById(1L)).thenReturn(Optional.empty());

        Optional<Order> result = getOrderByIdUseCase.execute(1L);

        assertTrue(result.isEmpty());
    }
}