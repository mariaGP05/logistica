package com.maria.logistica.application.order;

import com.maria.logistica.domain.model.Client;
import com.maria.logistica.domain.model.LogisticService;
import com.maria.logistica.domain.model.Order;
import com.maria.logistica.domain.model.OrderStatus;
import com.maria.logistica.domain.model.OrderSummary;
import com.maria.logistica.domain.model.ServiceType;
import com.maria.logistica.domain.port.output.OrderRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetOrderSummaryUseCaseImplTest {

    @Mock
    private OrderRepositoryPort orderRepositoryPort;

    @InjectMocks
    private GetOrderSummaryUseCaseImpl getOrderSummaryUseCase;

    @Test
    void shouldBuildOrderSummaryCorrectly() {
        Client client1 = Client.builder().id(1L).email("maria@test.com").build();
        Client client2 = Client.builder().id(2L).email("belen@test.com").build();

        LogisticService express = LogisticService.builder().id(1L).name("Express").type(ServiceType.EXPRESS).build();
        LogisticService standard = LogisticService.builder().id(2L).name("Standard").type(ServiceType.STANDARD).build();

        Order order1 = Order.builder()
                .id(1L)
                .client(client1)
                .service(express)
                .status(OrderStatus.DELIVERED)
                .totalCost(new BigDecimal("100.00"))
                .distanceKm(100.0)
                .createdAt(LocalDateTime.now())
                .build();

        Order order2 = Order.builder()
                .id(2L)
                .client(client2)
                .service(express)
                .status(OrderStatus.CANCELLED)
                .totalCost(new BigDecimal("200.00"))
                .distanceKm(150.0)
                .createdAt(LocalDateTime.now())
                .build();

        Order order3 = Order.builder()
                .id(3L)
                .client(client1)
                .service(standard)
                .status(OrderStatus.DELIVERED)
                .totalCost(new BigDecimal("300.00"))
                .distanceKm(300.0)
                .createdAt(LocalDateTime.now())
                .build();

        when(orderRepositoryPort.findAll()).thenReturn(List.of(order1, order2, order3));

        OrderSummary result = getOrderSummaryUseCase.execute();

        assertNotNull(result);
        assertEquals(3L, result.getTotalOrders());
        assertEquals(new BigDecimal("400.00"), result.getTotalRevenue());
        assertNotNull(result.getMostExpensiveOrder());
        assertEquals(3L, result.getMostExpensiveOrder().getId());
        assertEquals(2L, result.getOrdersByServiceType().get(ServiceType.EXPRESS));
        assertEquals(1L, result.getOrdersByServiceType().get(ServiceType.STANDARD));
        assertEquals(new BigDecimal("100.00"), result.getRevenueByServiceType().get(ServiceType.EXPRESS));
        assertEquals(new BigDecimal("300.00"), result.getRevenueByServiceType().get(ServiceType.STANDARD));
        assertEquals(100.0, result.getMinDistance());
        assertEquals(300.0, result.getMaxDistance());
        assertEquals(200.0, result.getAvgDistance());
        assertEquals(400.0, result.getTotalDistance());
        assertEquals(List.of("belen@test.com", "maria@test.com"), result.getExpressClientEmails());
    }

    @Test
    void shouldReturnEmptySummaryWhenThereAreNoOrders() {
        when(orderRepositoryPort.findAll()).thenReturn(List.of());

        OrderSummary result = getOrderSummaryUseCase.execute();

        assertNotNull(result);
        assertEquals(0L, result.getTotalOrders());
        assertEquals(BigDecimal.ZERO, result.getTotalRevenue());
        assertNull(result.getMostExpensiveOrder());
        assertTrue(result.getOrdersByServiceType().isEmpty());
        assertTrue(result.getRevenueByServiceType().isEmpty());
        assertEquals(0.0, result.getMinDistance());
        assertEquals(0.0, result.getMaxDistance());
        assertEquals(0.0, result.getAvgDistance());
        assertEquals(0.0, result.getTotalDistance());
        assertTrue(result.getExpressClientEmails().isEmpty());
    }

    @Test
    void shouldIgnoreNullServiceInvalidEmailsAndCancelledOrdersForSpecificCalculations() {
        Client blankEmailClient = Client.builder().id(1L).email("   ").build();

        LogisticService express = LogisticService.builder()
                .id(1L)
                .name("Express")
                .type(ServiceType.EXPRESS)
                .build();

        Order cancelledExpress = Order.builder()
                .id(1L)
                .client(blankEmailClient)
                .service(express)
                .status(OrderStatus.CANCELLED)
                .totalCost(new BigDecimal("500.00"))
                .distanceKm(50.0)
                .createdAt(LocalDateTime.now())
                .build();

        Order cancelledWithoutClient = Order.builder()
                .id(2L)
                .client(null)
                .service(express)
                .status(OrderStatus.CANCELLED)
                .totalCost(new BigDecimal("700.00"))
                .distanceKm(80.0)
                .createdAt(LocalDateTime.now())
                .build();

        Order deliveredWithoutService = Order.builder()
                .id(3L)
                .client(Client.builder().id(2L).email("maria@test.com").build())
                .service(null)
                .status(OrderStatus.DELIVERED)
                .totalCost(new BigDecimal("100.00"))
                .distanceKm(120.0)
                .createdAt(LocalDateTime.now())
                .build();

        when(orderRepositoryPort.findAll()).thenReturn(List.of(
                cancelledExpress,
                cancelledWithoutClient,
                deliveredWithoutService
        ));

        OrderSummary result = getOrderSummaryUseCase.execute();

        assertNotNull(result);
        assertEquals(3L, result.getTotalOrders());
        assertEquals(new BigDecimal("100.00"), result.getTotalRevenue());
        assertNotNull(result.getMostExpensiveOrder());
        assertEquals(3L, result.getMostExpensiveOrder().getId());
        assertEquals(2L, result.getOrdersByServiceType().get(ServiceType.EXPRESS));
        assertFalse(result.getRevenueByServiceType().containsKey(ServiceType.EXPRESS));
        assertEquals(120.0, result.getMinDistance());
        assertEquals(120.0, result.getMaxDistance());
        assertEquals(120.0, result.getAvgDistance());
        assertEquals(120.0, result.getTotalDistance());
        assertTrue(result.getExpressClientEmails().isEmpty());
    }

    @Test
    void shouldIgnoreNullEmailsAndReturnDistinctSortedExpressEmails() {
        LogisticService express = LogisticService.builder()
                .id(1L)
                .name("Express")
                .type(ServiceType.EXPRESS)
                .build();

        Order order1 = Order.builder()
                .id(1L)
                .client(Client.builder().id(1L).email(null).build())
                .service(express)
                .status(OrderStatus.DELIVERED)
                .totalCost(new BigDecimal("10.00"))
                .distanceKm(10.0)
                .createdAt(LocalDateTime.now())
                .build();

        Order order2 = Order.builder()
                .id(2L)
                .client(Client.builder().id(2L).email("belen@test.com").build())
                .service(express)
                .status(OrderStatus.DELIVERED)
                .totalCost(new BigDecimal("20.00"))
                .distanceKm(20.0)
                .createdAt(LocalDateTime.now())
                .build();

        Order order3 = Order.builder()
                .id(3L)
                .client(Client.builder().id(3L).email("maria@test.com").build())
                .service(express)
                .status(OrderStatus.DELIVERED)
                .totalCost(new BigDecimal("30.00"))
                .distanceKm(30.0)
                .createdAt(LocalDateTime.now())
                .build();

        Order order4 = Order.builder()
                .id(4L)
                .client(Client.builder().id(4L).email("belen@test.com").build())
                .service(express)
                .status(OrderStatus.CANCELLED)
                .totalCost(new BigDecimal("40.00"))
                .distanceKm(40.0)
                .createdAt(LocalDateTime.now())
                .build();

        when(orderRepositoryPort.findAll()).thenReturn(List.of(order1, order2, order3, order4));

        OrderSummary result = getOrderSummaryUseCase.execute();

        assertEquals(List.of("belen@test.com", "maria@test.com"), result.getExpressClientEmails());
    }
}