package com.maria.logistica.application.service;

import com.maria.logistica.domain.model.LogisticService;
import com.maria.logistica.domain.port.input.CreateLogisticServiceUseCase;
import com.maria.logistica.domain.port.output.LogisticServiceRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateLogisticServiceUseCaseImpl implements CreateLogisticServiceUseCase {

    private final LogisticServiceRepositoryPort logisticServiceRepositoryPort;

    @Override
    public LogisticService execute(LogisticService service) {
        service.setId(null);
        service.setAvailable(true);

        LogisticService saved = logisticServiceRepositoryPort.save(service);

        log.info("Servicio logístico creado correctamente. ID: {}", saved.getId());

        return saved;
    }
}