package com.maria.logistica.domain.port.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderCommand {
    private Long clientId;
    private Long serviceId;
    private String originCity;
    private String destinationCity;
    private Double distanceKm;
    private String notes;
}