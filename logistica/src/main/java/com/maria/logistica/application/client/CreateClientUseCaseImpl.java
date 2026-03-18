package com.maria.logistica.application.client;

import com.maria.logistica.domain.model.Client;
import com.maria.logistica.domain.port.input.CreateClientUseCase;
import com.maria.logistica.domain.port.output.ClientRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateClientUseCaseImpl implements CreateClientUseCase {

    private final ClientRepositoryPort clientRepositoryPort;

    @Override
    public Client execute(Client client) {
        client.setId(null);
        client.setRegisteredAt(LocalDateTime.now());
        client.setActive(true);

        Client saved = clientRepositoryPort.save(client);
        log.info("Cliente creado correctamente. ID: {}", saved.getId());

        return saved;
    }
}