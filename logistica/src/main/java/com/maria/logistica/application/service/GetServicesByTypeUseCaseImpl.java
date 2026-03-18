package com.maria.logistica.application.service;

import com.maria.logistica.domain.model.LogisticService;
import com.maria.logistica.domain.model.ServiceType;
import com.maria.logistica.domain.port.input.GetServicesByTypeUseCase;
import com.maria.logistica.domain.port.output.LogisticServiceRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetServicesByTypeUseCaseImpl implements GetServicesByTypeUseCase {

    private final LogisticServiceRepositoryPort logisticServiceRepositoryPort;

    @Override
    public List<LogisticService> execute(ServiceType type) {
        return logisticServiceRepositoryPort.findAll().stream()
                .filter(service -> Boolean.TRUE.equals(service.getAvailable()))
                .filter(service -> service.getType() == type)
                .toList();
    }
}