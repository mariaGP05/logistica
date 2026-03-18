package com.maria.logistica.domain.port.input;

public interface CancelOrderUseCase {
    boolean execute(Long orderId);
}