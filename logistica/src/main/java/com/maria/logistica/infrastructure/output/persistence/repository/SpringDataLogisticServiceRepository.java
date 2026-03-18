package com.maria.logistica.infrastructure.output.persistence.repository;

import com.maria.logistica.domain.model.ServiceType;
import com.maria.logistica.infrastructure.output.persistence.entity.LogisticServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SpringDataLogisticServiceRepository extends JpaRepository<LogisticServiceEntity, Long> {
    List<LogisticServiceEntity> findByType(ServiceType type);
}