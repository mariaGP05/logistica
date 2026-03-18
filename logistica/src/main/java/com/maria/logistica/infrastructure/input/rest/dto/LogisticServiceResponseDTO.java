package com.maria.logistica.infrastructure.input.rest.dto;

import com.maria.logistica.domain.model.ServiceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogisticServiceResponseDTO {
    private Long id;
    private String name;
    private ServiceType type;
    private BigDecimal pricePerKm;
    private String description;
    private Boolean available;
}