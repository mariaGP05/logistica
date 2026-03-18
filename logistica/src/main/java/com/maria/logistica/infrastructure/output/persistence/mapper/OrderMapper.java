package com.maria.logistica.infrastructure.output.persistence.mapper;

import com.maria.logistica.domain.model.Order;
import com.maria.logistica.infrastructure.input.rest.dto.OrderResponseDTO;
import com.maria.logistica.infrastructure.output.persistence.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ClientMapper.class, LogisticServiceMapper.class})
public interface OrderMapper {

    // salida REST: Dominio -> DTO
    @Mapping(target = "clientId", source = "client.id")
    @Mapping(target = "clientEmail", source = "client.email")
    @Mapping(target = "serviceId", source = "service.id")
    @Mapping(target = "serviceName", source = "service.name")
    @Mapping(target = "serviceType", source = "service.type")
    OrderResponseDTO toResponseDto(Order order);

    // persistencia: Dominio <-> Entity
    Order toDomain(OrderEntity entity);
    OrderEntity toEntity(Order domain);
}