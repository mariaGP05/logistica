package com.maria.logistica.domain.port.input;

import java.math.BigDecimal;

public interface GetTotalSpentByClientUseCase {
    BigDecimal execute(Long clientId);
}