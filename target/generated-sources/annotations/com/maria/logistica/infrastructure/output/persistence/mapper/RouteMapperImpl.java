package com.maria.logistica.infrastructure.output.persistence.mapper;

import com.maria.logistica.domain.model.Route;
import com.maria.logistica.domain.model.RouteStatus;
import com.maria.logistica.infrastructure.input.rest.dto.RouteRequestDTO;
import com.maria.logistica.infrastructure.input.rest.dto.RouteResponseDTO;
import com.maria.logistica.infrastructure.output.persistence.entity.RouteEntity;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-05T12:45:24+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class RouteMapperImpl implements RouteMapper {

    @Autowired
    private VehicleMapper vehicleMapper;

    @Override
    public Route toDomain(RouteRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Route.RouteBuilder route = Route.builder();

        route.origin( dto.getOrigin() );
        route.destination( dto.getDestination() );
        route.distance( dto.getDistance() );
        route.duration( dto.getDuration() );
        route.shipmentDate( dto.getShipmentDate() );
        if ( dto.getStatus() != null ) {
            route.status( Enum.valueOf( RouteStatus.class, dto.getStatus() ) );
        }

        return route.build();
    }

    @Override
    public RouteResponseDTO toResponseDto(Route route) {
        if ( route == null ) {
            return null;
        }

        RouteResponseDTO.RouteResponseDTOBuilder routeResponseDTO = RouteResponseDTO.builder();

        routeResponseDTO.id( route.getId() );
        routeResponseDTO.origin( route.getOrigin() );
        routeResponseDTO.destination( route.getDestination() );
        routeResponseDTO.distance( route.getDistance() );
        routeResponseDTO.duration( route.getDuration() );
        routeResponseDTO.shipmentDate( route.getShipmentDate() );
        if ( route.getStatus() != null ) {
            routeResponseDTO.status( route.getStatus().name() );
        }

        return routeResponseDTO.build();
    }

    @Override
    public RouteEntity toEntity(Route route) {
        if ( route == null ) {
            return null;
        }

        RouteEntity.RouteEntityBuilder routeEntity = RouteEntity.builder();

        routeEntity.id( route.getId() );
        routeEntity.origin( route.getOrigin() );
        routeEntity.destination( route.getDestination() );
        routeEntity.distance( route.getDistance() );
        routeEntity.duration( route.getDuration() );
        routeEntity.shipmentDate( route.getShipmentDate() );
        routeEntity.status( route.getStatus() );
        routeEntity.vehicleAssigned( vehicleMapper.toEntity( route.getVehicleAssigned() ) );

        return routeEntity.build();
    }

    @Override
    public Route toDomain(RouteEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Route.RouteBuilder route = Route.builder();

        route.id( entity.getId() );
        route.origin( entity.getOrigin() );
        route.destination( entity.getDestination() );
        route.distance( entity.getDistance() );
        route.duration( entity.getDuration() );
        route.shipmentDate( entity.getShipmentDate() );
        route.status( entity.getStatus() );
        route.vehicleAssigned( vehicleMapper.toDomain( entity.getVehicleAssigned() ) );

        return route.build();
    }
}
