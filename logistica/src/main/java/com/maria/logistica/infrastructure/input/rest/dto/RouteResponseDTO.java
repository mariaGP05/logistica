package com.maria.logistica.infrastructure.input.rest.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class RouteResponseDTO {
    private Long id;
    private String origin;
    private String destination;
    private Double distance;
    private Integer duration;
    private LocalDate shipmentDate;
    private String status;
    private String vehicleLicensePlate;
}