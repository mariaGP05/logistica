package com.maria.logistica.infrastructure.output.persistence.adapter;

import com.maria.logistica.domain.model.LogisticService;
import com.maria.logistica.domain.model.ServiceType;
import com.maria.logistica.domain.port.output.LogisticServiceRepositoryPort;
import com.maria.logistica.infrastructure.output.persistence.mapper.LogisticServiceMapper;
import com.maria.logistica.infrastructure.output.persistence.repository.SpringDataLogisticServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LogisticServiceRepositoryAdapter implements LogisticServiceRepositoryPort {

    private final SpringDataLogisticServiceRepository repository;
    private final LogisticServiceMapper mapper;

    @Override
    public LogisticService save(LogisticService service) {
        return mapper.toDomain(repository.save(mapper.toEntity(service)));
    }

    @Override
    public List<LogisticService> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<LogisticService> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<LogisticService> findByType(ServiceType type) {
        return repository.findByType(type).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}