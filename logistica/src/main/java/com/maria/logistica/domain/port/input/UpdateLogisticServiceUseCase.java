package com.maria.logistica.domain.port.input;

import com.maria.logistica.domain.model.LogisticService;
import java.util.Optional;

public interface UpdateLogisticServiceUseCase {
    Optional<LogisticService> execute(Long id, LogisticService service);
}