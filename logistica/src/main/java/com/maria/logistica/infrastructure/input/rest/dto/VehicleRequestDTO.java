package com.maria.logistica.infrastructure.input.rest.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleRequestDTO {

    @NotBlank(message = "La matrícula es obligatoria")
    @Size(min = 7, max = 7, message = "La matrícula debe tener exactamente 7 caracteres")
    private String licensePlate;

    @NotBlank(message = "La marca es obligatoria")
    @Size(max = 50, message = "La marca no puede exceder los 50 caracteres")
    private String brand;

    @NotBlank(message = "El modelo es obligatorio")
    @Size(max = 50, message = "El modelo no puede exceder los 50 caracteres")
    private String model;
}