package com.maria.logistica.application.order;

import com.maria.logistica.domain.model.Order;
import com.maria.logistica.domain.port.input.GetOrdersByDateRangeUseCase;
import com.maria.logistica.domain.port.output.OrderRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class GetOrdersByDateRangeUseCaseImpl implements GetOrdersByDateRangeUseCase {

    private final OrderRepositoryPort orderRepositoryPort;

    @Override
    public List<Order> execute(LocalDateTime from, LocalDateTime to) {
        Predicate<Order> afterFrom = order -> !order.getCreatedAt().isBefore(from);
        Predicate<Order> beforeTo = order -> !order.getCreatedAt().isAfter(to);

        return orderRepositoryPort.findAll().stream()
                .filter(afterFrom.and(beforeTo))
                .sorted(Comparator.comparing(Order::getCreatedAt).reversed())
                .toList();
    }
}