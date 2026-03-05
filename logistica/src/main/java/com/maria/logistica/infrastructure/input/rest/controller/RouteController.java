package com.maria.logistica.infrastructure.input.rest.controller;

import com.maria.logistica.domain.model.Route;
import com.maria.logistica.domain.model.Vehicle;
import com.maria.logistica.domain.port.input.*;
import com.maria.logistica.infrastructure.input.rest.dto.RouteRequestDTO;
import com.maria.logistica.infrastructure.input.rest.dto.RouteResponseDTO;
import com.maria.logistica.infrastructure.output.persistence.mapper.RouteMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/routes")
@RequiredArgsConstructor
public class RouteController {

    private final CreateRouteUseCase createRouteUseCase;
    private final GetAllRoutesUseCase getAllRoutesUseCase;
    private final GetRouteByIdUseCase getRouteByIdUseCase;
    private final UpdateRouteUseCase updateRouteUseCase;
    private final DeleteRouteUseCase deleteRouteUseCase;
    private final AssignVehicleToRouteUseCase assignVehicleToRouteUseCase;
    private final GetVehicleAssignedToRouteUseCase getVehicleAssignedToRouteUseCase;
    private final RemoveVehicleFromRouteUseCase removeVehicleFromRouteUseCase;

    private final RouteMapper routeMapper;

    @GetMapping
    public ResponseEntity<List<RouteResponseDTO>> getAllRoutes() {
        List<Route> routes = getAllRoutesUseCase.execute();

        if (routes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<RouteResponseDTO> response = routes.stream()
                .map(routeMapper::toResponseDto)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RouteResponseDTO> getRouteById(@PathVariable Long id) {
        return getRouteByIdUseCase.execute(id)
                .map(routeMapper::toResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RouteResponseDTO> createRoute(
            @Valid @RequestBody RouteRequestDTO routeRequestDTO) {

        Route route = routeMapper.toDomain(routeRequestDTO);
        Route created = createRouteUseCase.execute(route);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(routeMapper.toResponseDto(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RouteResponseDTO> updateRoute(
            @PathVariable Long id,
            @Valid @RequestBody RouteRequestDTO routeRequestDTO) {

        Route route = routeMapper.toDomain(routeRequestDTO);

        return updateRouteUseCase.execute(id, route)
                .map(routeMapper::toResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoute(@PathVariable Long id) {
        boolean deleted = deleteRouteUseCase.execute(id);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/vehicle")
    public ResponseEntity<RouteResponseDTO> assignVehicleToRoute(
            @PathVariable Long id,
            @RequestParam String licensePlate) {

        return assignVehicleToRouteUseCase.execute(id, licensePlate)
                .map(routeMapper::toResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/vehicle")
    public ResponseEntity<Vehicle> getVehicleAssignedToRoute(@PathVariable Long id) {
        return getVehicleAssignedToRouteUseCase.execute(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}/vehicle")
    public ResponseEntity<Void> removeVehicleFromRoute(@PathVariable Long id) {
        boolean removed = removeVehicleFromRouteUseCase.execute(id);
        return removed
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
