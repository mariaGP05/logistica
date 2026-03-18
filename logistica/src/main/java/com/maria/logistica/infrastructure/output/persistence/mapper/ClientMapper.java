package com.maria.logistica.infrastructure.output.persistence.mapper;

import com.maria.logistica.domain.model.Client;
import com.maria.logistica.infrastructure.input.rest.dto.ClientRequestDTO;
import com.maria.logistica.infrastructure.input.rest.dto.ClientResponseDTO;
import com.maria.logistica.infrastructure.output.persistence.entity.ClientEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    Client toDomain(ClientRequestDTO dto);
    ClientResponseDTO toResponseDto(Client client);

    Client toDomain(ClientEntity entity);
    ClientEntity toEntity(Client domain);
}