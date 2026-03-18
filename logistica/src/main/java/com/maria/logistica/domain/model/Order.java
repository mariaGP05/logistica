package com.maria.logistica.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Long id;
    private Client client;
    private LogisticService service;
    private String originCity;
    private String destinationCity;
    private Double distanceKm;
    private BigDecimal totalCost;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime deliveredAt;
    private String notes;
}