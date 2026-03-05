package com.maria.logistica.infrastructure.input.rest.controller;

import com.maria.logistica.domain.model.Route;
import com.maria.logistica.domain.port.input.GetRoutesByVehicleUseCase;
import com.maria.logistica.infrastructure.input.rest.dto.RouteResponseDTO;
import com.maria.logistica.infrastructure.output.persistence.mapper.RouteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicles/{plate}/routes")
@RequiredArgsConstructor
public class VehicleRoutesController {

    private final GetRoutesByVehicleUseCase getRoutesByVehicleUseCase;
    private final RouteMapper routeMapper;

    @GetMapping
    public ResponseEntity<List<RouteResponseDTO>> getRoutesByVehicle(
            @PathVariable String plate) {

        List<Route> routes = getRoutesByVehicleUseCase.execute(plate);

        if (routes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<RouteResponseDTO> response = routes.stream()
                .map(routeMapper::toResponseDto)
                .toList();

        return ResponseEntity.ok(response);
    }
}
