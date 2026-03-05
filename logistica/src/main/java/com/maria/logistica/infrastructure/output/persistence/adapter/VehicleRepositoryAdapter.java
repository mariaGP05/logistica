package com.maria.logistica.infrastructure.output.persistence.adapter;

import com.maria.logistica.domain.model.Vehicle;
import com.maria.logistica.domain.port.output.VehicleRepositoryPort;
import com.maria.logistica.infrastructure.output.persistence.mapper.VehicleMapper;
import com.maria.logistica.infrastructure.output.persistence.repository.SpringDataVehicleRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class VehicleRepositoryAdapter implements VehicleRepositoryPort {

    private final SpringDataVehicleRepository springDataRepository;
    private final VehicleMapper vehicleMapper;

    @Override
    public List<Vehicle> findAll() {
        return springDataRepository.findAll()
                .stream()
                .map(vehicleMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Vehicle> findByLicensePlate(String licensePlate) {
        return springDataRepository.findByLicensePlate(licensePlate)
                .map(vehicleMapper::toDomain);
    }

    @Override
    public Vehicle save(Vehicle vehicle) {
        var entity = vehicleMapper.toEntity(vehicle);
        var saved = springDataRepository.save(entity);
        return vehicleMapper.toDomain(saved);
    }

    //damos permiso a springDataRepository para relizar la consulta
    @Transactional
    @Override
    public void deleteByLicensePlate(String licensePlate) {
        springDataRepository.deleteByLicensePlate(licensePlate);
    }
}

