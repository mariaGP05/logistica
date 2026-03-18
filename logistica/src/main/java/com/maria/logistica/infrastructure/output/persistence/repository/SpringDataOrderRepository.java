package com.maria.logistica.infrastructure.output.persistence.repository;

import com.maria.logistica.infrastructure.output.persistence.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SpringDataOrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByClientId(Long clientId);
    List<OrderEntity> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to);
}