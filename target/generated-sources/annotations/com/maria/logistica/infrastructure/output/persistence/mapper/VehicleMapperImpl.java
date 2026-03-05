package com.maria.logistica.infrastructure.output.persistence.mapper;

import com.maria.logistica.domain.model.Vehicle;
import com.maria.logistica.infrastructure.input.rest.dto.VehicleRequestDTO;
import com.maria.logistica.infrastructure.input.rest.dto.VehicleResponseDTO;
import com.maria.logistica.infrastructure.output.persistence.entity.VehicleEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-05T12:45:24+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class VehicleMapperImpl implements VehicleMapper {

    @Override
    public Vehicle toDomain(VehicleRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Vehicle.VehicleBuilder vehicle = Vehicle.builder();

        vehicle.licensePlate( dto.getLicensePlate() );
        vehicle.brand( dto.getBrand() );
        vehicle.model( dto.getModel() );

        return vehicle.build();
    }

    @Override
    public VehicleResponseDTO toResponseDto(Vehicle vehicle) {
        if ( vehicle == null ) {
            return null;
        }

        VehicleResponseDTO.VehicleResponseDTOBuilder vehicleResponseDTO = VehicleResponseDTO.builder();

        vehicleResponseDTO.id( vehicle.getId() );
        vehicleResponseDTO.licensePlate( vehicle.getLicensePlate() );
        vehicleResponseDTO.brand( vehicle.getBrand() );
        vehicleResponseDTO.model( vehicle.getModel() );

        return vehicleResponseDTO.build();
    }

    @Override
    public VehicleEntity toEntity(Vehicle vehicle) {
        if ( vehicle == null ) {
            return null;
        }

        VehicleEntity.VehicleEntityBuilder vehicleEntity = VehicleEntity.builder();

        vehicleEntity.id( vehicle.getId() );
        vehicleEntity.licensePlate( vehicle.getLicensePlate() );
        vehicleEntity.brand( vehicle.getBrand() );
        vehicleEntity.model( vehicle.getModel() );

        return vehicleEntity.build();
    }

    @Override
    public Vehicle toDomain(VehicleEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Vehicle.VehicleBuilder vehicle = Vehicle.builder();

        vehicle.id( entity.getId() );
        vehicle.licensePlate( entity.getLicensePlate() );
        vehicle.brand( entity.getBrand() );
        vehicle.model( entity.getModel() );

        return vehicle.build();
    }
}
