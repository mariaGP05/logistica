package com.maria.logistica.application.order;

import com.maria.logistica.domain.model.Order;
import com.maria.logistica.domain.model.ServiceType;
import com.maria.logistica.domain.port.input.GetOrdersByServiceTypeUseCase;
import com.maria.logistica.domain.port.output.OrderRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetOrdersByServiceTypeUseCaseImpl implements GetOrdersByServiceTypeUseCase {

    private final OrderRepositoryPort orderRepositoryPort;

    @Override
    public List<Order> execute(ServiceType type) {
        return orderRepositoryPort.findAll().stream()
                .filter(order -> order.getService() != null)
                .filter(order -> order.getService().getType() == type)
                .toList();
    }
}