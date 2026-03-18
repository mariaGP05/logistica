package com.maria.logistica.application.order;

import com.maria.logistica.domain.model.Order;
import com.maria.logistica.domain.port.input.GetOrdersByClientUseCase;
import com.maria.logistica.domain.port.output.OrderRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetOrdersByClientUseCaseImpl implements GetOrdersByClientUseCase {

    private final OrderRepositoryPort orderRepositoryPort;

    @Override
    public List<Order> execute(Long clientId) {
        return orderRepositoryPort.findByClientId(clientId).stream()
                .sorted(Comparator.comparing(Order::getCreatedAt).reversed())
                .toList();
    }
}