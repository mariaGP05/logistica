package com.maria.logistica.infrastructure.input.rest.dto;

import com.maria.logistica.domain.model.ServiceType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogisticServiceRequestDTO {

    @NotBlank(message = "El nombre del servicio es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
    private String name;

    @NotNull(message = "El tipo de servicio es obligatorio")
    private ServiceType type;

    @NotNull(message = "El precio por km es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio por km debe ser mayor o igual a 0.01")
    private BigDecimal pricePerKm;

    @Size(max = 500, message = "La descripción no puede exceder los 500 caracteres")
    private String description;
}