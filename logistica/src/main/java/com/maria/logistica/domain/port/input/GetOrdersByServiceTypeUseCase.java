package com.maria.logistica.domain.port.input;

import com.maria.logistica.domain.model.Order;
import com.maria.logistica.domain.model.ServiceType;
import java.util.List;

public interface GetOrdersByServiceTypeUseCase {
    List<Order> execute(ServiceType type);
}