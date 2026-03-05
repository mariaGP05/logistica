package com.maria.logistica.infrastructure.output.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vehicle", indexes = {
    @Index(name = "idx_license_plate", columnList = "license_plate")
})
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehicleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "license_plate", unique = true, nullable = false, length = 20)
    private String licensePlate;

    @Column(name = "brand", nullable = false, length = 100)
    private String brand;

    @Column(name = "model", nullable = false, length = 100)
    private String model;
}

