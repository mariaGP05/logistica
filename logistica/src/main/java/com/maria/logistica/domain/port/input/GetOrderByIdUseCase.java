package com.maria.logistica.domain.port.input;

import com.maria.logistica.domain.model.Order;
import java.util.Optional;

public interface GetOrderByIdUseCase {
    Optional<Order> execute(Long id);
}