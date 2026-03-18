package com.maria.logistica.application.client;

import com.maria.logistica.domain.model.Client;
import com.maria.logistica.domain.port.input.GetClientByIdUseCase;
import com.maria.logistica.domain.port.output.ClientRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetClientByIdUseCaseImpl implements GetClientByIdUseCase {

    private final ClientRepositoryPort clientRepositoryPort;

    @Override
    public Optional<Client> execute(Long id) {
        return clientRepositoryPort.findById(id);
    }
}