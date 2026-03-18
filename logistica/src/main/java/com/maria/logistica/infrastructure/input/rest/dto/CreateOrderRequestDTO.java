package com.maria.logistica.infrastructure.input.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequestDTO {

    @NotNull(message = "El id del cliente es obligatorio")
    private Long clientId;

    @NotNull(message = "El id del servicio es obligatorio")
    private Long serviceId;

    @NotBlank(message = "La ciudad de origen es obligatoria")
    @Size(max = 150, message = "La ciudad de origen no puede exceder los 150 caracteres")
    private String originCity;

    @NotBlank(message = "La ciudad de destino es obligatoria")
    @Size(max = 150, message = "La ciudad de destino no puede exceder los 150 caracteres")
    private String destinationCity;

    @NotNull(message = "La distancia es obligatoria")
    @Positive(message = "La distancia debe ser mayor que 0")
    private Double distanceKm;

    @Size(max = 500, message = "Las notas no pueden exceder los 500 caracteres")
    private String notes;
}