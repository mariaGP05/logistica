package com.maria.logistica.domain.port.input;

import com.maria.logistica.domain.model.OrderSummary;

public interface GetOrderSummaryUseCase {
    OrderSummary execute();
}