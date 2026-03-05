package com.maria.logistica.infrastructure.input.rest.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class VehicleResponseDTO {
    private Long id;
    private String licensePlate;
    private String brand;
    private String model;
}