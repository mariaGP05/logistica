package com.maria.logistica.infrastructure.output.persistence.repository;

import com.maria.logistica.infrastructure.output.persistence.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SpringDataClientRepository extends JpaRepository<ClientEntity, Long> {
    Optional<ClientEntity> findByEmail(String email);
}