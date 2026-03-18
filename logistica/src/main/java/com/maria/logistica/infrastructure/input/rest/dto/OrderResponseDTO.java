package com.maria.logistica.infrastructure.input.rest.dto;

import com.maria.logistica.domain.model.OrderStatus;
import com.maria.logistica.domain.model.ServiceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
    private Long id;
    private Long clientId;
    private String clientEmail;
    private Long serviceId;
    private String serviceName;
    private ServiceType serviceType;
    private String originCity;
    private String destinationCity;
    private Double distanceKm;
    private BigDecimal totalCost;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime deliveredAt;
    private String notes;
}