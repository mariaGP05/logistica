package com.maria.logistica.application.order;

import com.maria.logistica.domain.model.Order;
import com.maria.logistica.domain.port.input.GetOrderByIdUseCase;
import com.maria.logistica.domain.port.output.OrderRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetOrderByIdUseCaseImpl implements GetOrderByIdUseCase {

    private final OrderRepositoryPort orderRepositoryPort;

    @Override
    public Optional<Order> execute(Long id) {
        return orderRepositoryPort.findById(id);
    }
}