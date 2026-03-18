package com.maria.logistica.infrastructure.output.persistence.entity;

import com.maria.logistica.domain.model.ServiceType;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "logistic_service")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogisticServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 30)
    private ServiceType type;

    @Column(name = "price_per_km", nullable = false, precision = 10, scale = 2)
    private BigDecimal pricePerKm;

    @Column(name = "description")
    private String description;

    @Column(name = "available", nullable = false)
    private Boolean available;
}