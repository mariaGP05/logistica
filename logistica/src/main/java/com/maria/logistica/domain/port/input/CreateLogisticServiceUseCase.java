package com.maria.logistica.domain.port.input;

import com.maria.logistica.domain.model.LogisticService;

public interface CreateLogisticServiceUseCase {
    LogisticService execute(LogisticService service);
}