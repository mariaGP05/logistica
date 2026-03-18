package com.maria.logistica.infrastructure.output.persistence.mapper;

import com.maria.logistica.domain.model.LogisticService;
import com.maria.logistica.infrastructure.input.rest.dto.LogisticServiceRequestDTO;
import com.maria.logistica.infrastructure.input.rest.dto.LogisticServiceResponseDTO;
import com.maria.logistica.infrastructure.output.persistence.entity.LogisticServiceEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LogisticServiceMapper {

    LogisticService toDomain(LogisticServiceRequestDTO dto);
    LogisticServiceResponseDTO toResponseDto(LogisticService service);

    LogisticService toDomain(LogisticServiceEntity entity);
    LogisticServiceEntity toEntity(LogisticService domain);
}