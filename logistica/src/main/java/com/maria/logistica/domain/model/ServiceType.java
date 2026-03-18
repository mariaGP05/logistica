package com.maria.logistica.domain.model;

import lombok.Getter;

@Getter
public enum ServiceType {
    STANDARD(1.0),
    EXPRESS(1.8),
    REFRIGERATED(2.2),
    HEAVY(1.5),
    DOCUMENT(0.7);

    private final double multiplier;

    ServiceType(double multiplier) {
        this.multiplier = multiplier;
    }

}