package com.maria.logistica.domain.port.output;

import com.maria.logistica.domain.model.Order;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepositoryPort {
    Order save(Order order);
    Optional<Order> findById(Long id);
    List<Order> findAll();
    List<Order> findByClientId(Long clientId);
    List<Order> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to);
}