package com.maria.logistica.infrastructure.output.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import com.maria.logistica.domain.model.RouteStatus;

@Entity
@Table(name = "route", indexes = {
    @Index(name = "idx_status", columnList = "status"),
    @Index(name = "idx_shipment_date", columnList = "shipment_date")
})
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RouteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "origin", nullable = false, length = 255)
    private String origin;

    @Column(name = "destination", nullable = false, length = 255)
    private String destination;

    @Column(name = "distance", nullable = false)
    private Double distance;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Column(name = "shipment_date", nullable = false)
    private LocalDate shipmentDate;

    @Enumerated(EnumType.STRING) //guarda el nombre del enum como texto
    @Column(name = "status", nullable = false, length = 50)
    private RouteStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_assigned_id")
    private VehicleEntity vehicleAssigned;
}

