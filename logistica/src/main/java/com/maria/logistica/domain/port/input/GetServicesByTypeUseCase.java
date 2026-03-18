package com.maria.logistica.domain.port.input;

import com.maria.logistica.domain.model.LogisticService;
import com.maria.logistica.domain.model.ServiceType;
import java.util.List;

public interface GetServicesByTypeUseCase {
    List<LogisticService> execute(ServiceType type);
}