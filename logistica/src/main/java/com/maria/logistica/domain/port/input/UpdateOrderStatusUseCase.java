package com.maria.logistica.domain.port.input;

import com.maria.logistica.domain.model.OrderStatus;
import com.maria.logistica.domain.model.Order;
import java.util.Optional;

public interface UpdateOrderStatusUseCase {
    Optional<Order> execute(Long orderId, OrderStatus status);
}