package com.maria.logistica.domain.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Builder
@Slf4j
@AllArgsConstructor
@NoArgsConstructor

public class Vehicle {
    private Long id;
    private String licensePlate;
    private String brand;
    private String model;
}

