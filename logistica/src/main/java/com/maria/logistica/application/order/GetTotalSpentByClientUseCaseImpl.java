package com.maria.logistica.application.order;

import com.maria.logistica.domain.model.OrderStatus;
import com.maria.logistica.domain.port.input.GetTotalSpentByClientUseCase;
import com.maria.logistica.domain.port.output.OrderRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class GetTotalSpentByClientUseCaseImpl implements GetTotalSpentByClientUseCase {

    private final OrderRepositoryPort orderRepositoryPort;

    @Override
    public BigDecimal execute(Long clientId) {
        return orderRepositoryPort.findByClientId(clientId).stream()
                .filter(order -> order.getStatus() != OrderStatus.CANCELLED)
                .map(order -> order.getTotalCost() == null ? BigDecimal.ZERO : order.getTotalCost())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}