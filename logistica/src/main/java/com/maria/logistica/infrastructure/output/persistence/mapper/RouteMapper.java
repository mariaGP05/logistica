package com.maria.logistica.infrastructure.output.persistence.mapper;

import com.maria.logistica.domain.model.Route;
import com.maria.logistica.infrastructure.input.rest.dto.RouteRequestDTO;
import com.maria.logistica.infrastructure.input.rest.dto.RouteResponseDTO;
import com.maria.logistica.infrastructure.output.persistence.entity.RouteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {VehicleMapper.class})
public interface RouteMapper {
    //entrada: DTO -> Dominio
    Route toDomain(RouteRequestDTO dto);

    //salida: Dominio -> DTO
    RouteResponseDTO toResponseDto(Route route);

    //persistencia: Dominio <-> Entity
    RouteEntity toEntity(Route route);
    Route toDomain(RouteEntity entity);
}

