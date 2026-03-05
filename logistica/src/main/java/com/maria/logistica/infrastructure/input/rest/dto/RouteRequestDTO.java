package com.maria.logistica.infrastructure.input.rest.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteRequestDTO {

    @NotBlank(message = "El origen no puede estar vacío")
    @Size(min = 3, max = 100, message = "El origen debe tener entre 3 y 100 caracteres")
    private String origin;

    @NotBlank(message = "El destino no puede estar vacío")
    @Size(min = 3, max = 100, message = "El destino debe tener entre 3 y 100 caracteres")
    private String destination;

    @NotNull(message = "La distancia no puede ser nula")
    @Positive(message = "La distancia debe ser un valor positivo")
    private Double distance;

    @NotNull(message = "La duración no puede ser nula")
    @Positive(message = "La duración debe ser un valor positivo")
    private Integer duration;

    @NotNull(message = "La fecha de envío no puede ser nula")
    @FutureOrPresent(message = "La fecha de envío debe ser hoy o en el futuro")
    private LocalDate shipmentDate;

    @NotBlank(message = "El estado es obligatorio")
    private String status; //se recibe como String y MapStruct lo pasa a Enum

    private String vehicleLicensePlate;
}