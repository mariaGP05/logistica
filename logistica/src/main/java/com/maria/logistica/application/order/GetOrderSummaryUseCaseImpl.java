package com.maria.logistica.application.order;

import com.maria.logistica.domain.model.Order;
import com.maria.logistica.domain.model.OrderStatus;
import com.maria.logistica.domain.model.OrderSummary;
import com.maria.logistica.domain.model.ServiceType;
import com.maria.logistica.domain.port.input.GetOrderSummaryUseCase;
import com.maria.logistica.domain.port.output.OrderRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetOrderSummaryUseCaseImpl implements GetOrderSummaryUseCase {

    private final OrderRepositoryPort orderRepositoryPort;

    @Override
    public OrderSummary execute() {

        List<Order> orders = orderRepositoryPort.findAll();

        Map<ServiceType, Long> ordersByServiceType = orders.stream()
                .filter(order -> order.getService() != null)
                .collect(Collectors.groupingBy(
                        order -> order.getService().getType(),
                        Collectors.counting()
                ));

        Map<ServiceType, BigDecimal> revenueByServiceType = orders.stream()
                .filter(order -> order.getService() != null)
                .filter(order -> OrderStatus.DELIVERED.equals(order.getStatus()))
                .collect(Collectors.groupingBy(
                        order -> order.getService().getType(),
                        Collectors.mapping(
                                Order::getTotalCost,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                        )
                ));

        BigDecimal totalRevenue = orders.stream()
                .filter(order -> OrderStatus.DELIVERED.equals(order.getStatus()))
                .map(Order::getTotalCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order mostExpensiveOrder = orders.stream()
                .filter(order -> !OrderStatus.CANCELLED.equals(order.getStatus()))
                .max(Comparator.comparing(Order::getTotalCost))
                .orElse(null);

        DoubleSummaryStatistics distanceStats = orders.stream()
                .filter(order -> OrderStatus.DELIVERED.equals(order.getStatus()))
                .mapToDouble(Order::getDistanceKm)
                .summaryStatistics();

        List<String> expressClientEmails = orders.stream()
                .filter(order -> order.getService() != null)
                .filter(order -> ServiceType.EXPRESS.equals(order.getService().getType()))
                .filter(order -> order.getClient() != null)
                .map(order -> order.getClient().getEmail())
                .filter(email -> email != null && !email.isBlank())
                .distinct()
                .sorted()
                .toList();

        return OrderSummary.builder()
                .totalOrders((long) orders.size())
                .totalRevenue(totalRevenue)
                .mostExpensiveOrder(mostExpensiveOrder)
                .ordersByServiceType(ordersByServiceType)
                .revenueByServiceType(revenueByServiceType)
                .minDistance(distanceStats.getCount() > 0 ? distanceStats.getMin() : 0.0)
                .maxDistance(distanceStats.getCount() > 0 ? distanceStats.getMax() : 0.0)
                .avgDistance(distanceStats.getCount() > 0 ? distanceStats.getAverage() : 0.0)
                .totalDistance(distanceStats.getSum())
                .expressClientEmails(expressClientEmails)
                .build();
    }
}