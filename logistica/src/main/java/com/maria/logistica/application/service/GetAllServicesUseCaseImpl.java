package com.maria.logistica.application.service;

import com.maria.logistica.domain.model.LogisticService;
import com.maria.logistica.domain.port.input.GetAllServicesUseCase;
import com.maria.logistica.domain.port.output.LogisticServiceRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllServicesUseCaseImpl implements GetAllServicesUseCase {

    private final LogisticServiceRepositoryPort logisticServiceRepositoryPort;

    @Override
    public List<LogisticService> execute() {
        return logisticServiceRepositoryPort.findAll().stream()
                .filter(service -> Boolean.TRUE.equals(service.getAvailable()))
                .sorted(Comparator.comparing(LogisticService::getName))
                .toList();
    }
}