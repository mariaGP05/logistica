package com.maria.logistica.application.order;

import com.maria.logistica.domain.model.Order;
import com.maria.logistica.domain.model.OrderStatus;
import com.maria.logistica.domain.port.input.UpdateOrderStatusUseCase;
import com.maria.logistica.domain.port.output.OrderRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateOrderStatusUseCaseImpl implements UpdateOrderStatusUseCase {

    private final OrderRepositoryPort orderRepositoryPort;

    @Override
    public Optional<Order> execute(Long orderId, OrderStatus status) {
        Optional<Order> orderOpt = orderRepositoryPort.findById(orderId);

        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            order.setStatus(status);

            if (status == OrderStatus.DELIVERED) {
                order.setDeliveredAt(LocalDateTime.now());
            }

            Order updated = orderRepositoryPort.save(order);
            log.info("Estado del pedido {} actualizado a {}", orderId, status);

            return Optional.of(updated);
        }

        log.error("Pedido no encontrado con ID: {}", orderId);
        return Optional.empty();
    }
}