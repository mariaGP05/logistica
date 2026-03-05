package com.maria.logistica.infrastructure.output.persistence.mapper;

import com.maria.logistica.domain.model.Vehicle;
import com.maria.logistica.infrastructure.input.rest.dto.VehicleRequestDTO;
import com.maria.logistica.infrastructure.input.rest.dto.VehicleResponseDTO;
import com.maria.logistica.infrastructure.output.persistence.entity.VehicleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VehicleMapper {
    Vehicle toDomain(VehicleRequestDTO dto);
    VehicleResponseDTO toResponseDto(Vehicle vehicle);

    VehicleEntity toEntity(Vehicle vehicle);
    Vehicle toDomain(VehicleEntity entity);
}