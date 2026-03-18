package com.maria.logistica.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogisticService {
    private Long id;
    private String name;
    private ServiceType type;
    private BigDecimal pricePerKm;
    private String description;
    private Boolean available;
}