package com.maria.logistica.domain.port.input;

import com.maria.logistica.domain.model.Order;
import java.util.List;

public interface GetOrdersByClientUseCase {
    List<Order> execute(Long clientId);
}