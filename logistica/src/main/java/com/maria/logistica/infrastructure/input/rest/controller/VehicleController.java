package com.maria.logistica.infrastructure.input.rest.controller;

import com.maria.logistica.domain.model.Vehicle;
import com.maria.logistica.domain.port.input.*;
import com.maria.logistica.infrastructure.input.rest.dto.VehicleRequestDTO;
import com.maria.logistica.infrastructure.input.rest.dto.VehicleResponseDTO;
import com.maria.logistica.infrastructure.output.persistence.mapper.VehicleMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final CreateVehicleUseCase createVehicleUseCase;
    private final GetAllVehiclesUseCase getAllVehiclesUseCase;
    private final GetVehicleByLicensePlateUseCase getVehicleByLicensePlateUseCase;
    private final UpdateVehicleUseCase updateVehicleUseCase;
    private final DeleteVehicleUseCase deleteVehicleUseCase;

    private final VehicleMapper vehicleMapper;

    @GetMapping
    public ResponseEntity<List<VehicleResponseDTO>> getAllVehicles() {
        List<Vehicle> vehicles = getAllVehiclesUseCase.execute();

        if (vehicles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<VehicleResponseDTO> response = vehicles.stream()
                .map(vehicleMapper::toResponseDto)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{plate}")
    public ResponseEntity<VehicleResponseDTO> getVehicleByLicensePlate(
            @PathVariable String plate) {

        return getVehicleByLicensePlateUseCase.execute(plate)
                .map(vehicleMapper::toResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<VehicleResponseDTO> createVehicle(
            @Valid @RequestBody VehicleRequestDTO vehicleRequestDTO) {

        Vehicle vehicle = vehicleMapper.toDomain(vehicleRequestDTO);
        Vehicle created = createVehicleUseCase.execute(vehicle);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(vehicleMapper.toResponseDto(created));
    }

    @PutMapping("/{plate}")
    public ResponseEntity<VehicleResponseDTO> updateVehicle(
            @PathVariable String plate,
            @Valid @RequestBody VehicleRequestDTO vehicleRequestDTO) {

        Vehicle vehicle = vehicleMapper.toDomain(vehicleRequestDTO);

        return updateVehicleUseCase.execute(plate, vehicle)
                .map(vehicleMapper::toResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{plate}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable String plate) {
        boolean deleted = deleteVehicleUseCase.execute(plate);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
