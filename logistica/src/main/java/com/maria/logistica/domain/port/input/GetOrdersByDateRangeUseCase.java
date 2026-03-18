package com.maria.logistica.domain.port.input;

import com.maria.logistica.domain.model.Order;
import java.time.LocalDateTime;
import java.util.List;

public interface GetOrdersByDateRangeUseCase {
    List<Order> execute(LocalDateTime from, LocalDateTime to);
}