package com.maria.logistica.infrastructure.output.persistence.adapter;

import com.maria.logistica.domain.model.Route;
import com.maria.logistica.domain.port.output.RouteRepositoryPort;
import com.maria.logistica.infrastructure.output.persistence.mapper.RouteMapper;
import com.maria.logistica.infrastructure.output.persistence.repository.SpringDataRouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RouteRepositoryAdapter implements RouteRepositoryPort {
    private final SpringDataRouteRepository springDataRepository;
    private final RouteMapper routeMapper;

    @Override
    public List<Route> findAll() {
        return springDataRepository.findAll()
                .stream()
                .map(routeMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Route> findById(Long id) {
        return springDataRepository.findById(id)
                .map(routeMapper::toDomain);
    }

    @Override
    public Route save(Route route) {
        var entity = routeMapper.toEntity(route);
        var saved = springDataRepository.save(entity);
        return routeMapper.toDomain(saved);
    }

    @Override
    public void deleteById(Long id) {
        springDataRepository.deleteById(id);
    }

    @Override
    public List<Route> findByVehicleLicensePlate(String licensePlate) {
        return springDataRepository.findByVehicleLicensePlate(licensePlate)
                .stream()
                .map(routeMapper::toDomain)
                .toList();
    }
}