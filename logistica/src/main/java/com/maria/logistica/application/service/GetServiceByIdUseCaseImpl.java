package com.maria.logistica.application.service;

import com.maria.logistica.domain.model.LogisticService;
import com.maria.logistica.domain.port.input.GetServiceByIdUseCase;
import com.maria.logistica.domain.port.output.LogisticServiceRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetServiceByIdUseCaseImpl implements GetServiceByIdUseCase {

    private final LogisticServiceRepositoryPort logisticServiceRepositoryPort;

    @Override
    public Optional<LogisticService> execute(Long id) {
        return logisticServiceRepositoryPort.findById(id);
    }
}