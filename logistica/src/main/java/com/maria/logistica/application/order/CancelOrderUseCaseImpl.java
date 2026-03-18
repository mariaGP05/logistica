package com.maria.logistica.application.order;

import com.maria.logistica.domain.model.Order;
import com.maria.logistica.domain.model.OrderStatus;
import com.maria.logistica.domain.port.input.CancelOrderUseCase;
import com.maria.logistica.domain.port.output.OrderRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CancelOrderUseCaseImpl implements CancelOrderUseCase {

    private final OrderRepositoryPort orderRepositoryPort;

    @Override
    public boolean execute(Long orderId) {
        Optional<Order> orderOpt = orderRepositoryPort.findById(orderId);

        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            order.setStatus(OrderStatus.CANCELLED);
            orderRepositoryPort.save(order);

            log.info("Pedido cancelado correctamente. ID: {}", orderId);
            return true;
        }

        log.error("No se encontró el pedido con ID: {}", orderId);
        return false;
    }
}