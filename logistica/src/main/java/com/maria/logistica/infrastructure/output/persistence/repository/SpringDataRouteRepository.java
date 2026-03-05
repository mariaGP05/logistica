package com.maria.logistica.infrastructure.output.persistence.repository;

import com.maria.logistica.infrastructure.output.persistence.entity.RouteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SpringDataRouteRepository extends JpaRepository<RouteEntity, Long> {
    @Query("SELECT r FROM RouteEntity r WHERE r.vehicleAssigned.licensePlate = :licensePlate")
    List<RouteEntity> findByVehicleLicensePlate(String licensePlate);
}

