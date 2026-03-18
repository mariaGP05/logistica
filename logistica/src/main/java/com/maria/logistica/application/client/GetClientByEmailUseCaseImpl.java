package com.maria.logistica.application.client;

import com.maria.logistica.domain.model.Client;
import com.maria.logistica.domain.port.input.GetClientByEmailUseCase;
import com.maria.logistica.domain.port.output.ClientRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetClientByEmailUseCaseImpl implements GetClientByEmailUseCase {

    private final ClientRepositoryPort clientRepositoryPort;

    @Override
    public Optional<Client> execute(String email) {
        return clientRepositoryPort.findByEmail(email);
    }
}