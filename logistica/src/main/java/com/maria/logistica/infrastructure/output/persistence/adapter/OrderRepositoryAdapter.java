package com.maria.logistica.infrastructure.output.persistence.adapter;

import com.maria.logistica.domain.model.Order;
import com.maria.logistica.domain.port.output.OrderRepositoryPort;
import com.maria.logistica.infrastructure.output.persistence.mapper.OrderMapper;
import com.maria.logistica.infrastructure.output.persistence.repository.SpringDataOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderRepositoryAdapter implements OrderRepositoryPort {

    private final SpringDataOrderRepository repository;
    private final OrderMapper mapper;

    @Override
    public Order save(Order order) {
        return mapper.toDomain(repository.save(mapper.toEntity(order)));
    }

    @Override
    public Optional<Order> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Order> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Order> findByClientId(Long clientId) {
        return repository.findByClientId(clientId).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Order> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to) {
        return repository.findByCreatedAtBetween(from, to).stream()
                .map(mapper::toDomain)
                .toList();
    }
}