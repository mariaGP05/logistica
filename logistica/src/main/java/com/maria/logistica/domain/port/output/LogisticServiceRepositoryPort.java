package com.maria.logistica.domain.port.output;

import com.maria.logistica.domain.model.LogisticService;
import com.maria.logistica.domain.model.ServiceType;
import java.util.List;
import java.util.Optional;

public interface LogisticServiceRepositoryPort {
    LogisticService save(LogisticService service);
    List<LogisticService> findAll();
    Optional<LogisticService> findById(Long id);
    List<LogisticService> findByType(ServiceType type);
    void deleteById(Long id);
}