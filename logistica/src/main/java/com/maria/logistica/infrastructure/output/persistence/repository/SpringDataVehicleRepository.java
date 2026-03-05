package com.maria.logistica.infrastructure.output.persistence.repository;

import com.maria.logistica.infrastructure.output.persistence.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SpringDataVehicleRepository extends JpaRepository<VehicleEntity, Long> {
    Optional<VehicleEntity> findByLicensePlate(String licensePlate);
    void deleteByLicensePlate(String licensePlate);
}

