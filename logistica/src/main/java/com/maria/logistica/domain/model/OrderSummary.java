package com.maria.logistica.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderSummary {
    private Long totalOrders;
    private BigDecimal totalRevenue;
    private Order mostExpensiveOrder;
    private Map<ServiceType, Long> ordersByServiceType;
    private Map<ServiceType, BigDecimal> revenueByServiceType;
    private Double minDistance;
    private Double maxDistance;
    private Double avgDistance;
    private Double totalDistance;
    private List<String> expressClientEmails;
}