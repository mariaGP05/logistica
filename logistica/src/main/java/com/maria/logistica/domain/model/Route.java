package com.maria.logistica.domain.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import jakarta.persistence.*; //JPA
import java.time.LocalDate;
import com.maria.logistica.domain.model.RouteStatus;

@Getter
@Setter
@Builder
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class Route {
    private Long id;
    private String origin;
    private String destination;
    private Double distance;
    private Integer duration;
    private LocalDate shipmentDate;
    private RouteStatus status;
    private Vehicle vehicleAssigned;
}