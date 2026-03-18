package com.maria.logistica.application.client;

import com.maria.logistica.domain.model.Client;
import com.maria.logistica.domain.port.input.UpdateClientUseCase;
import com.maria.logistica.domain.port.output.ClientRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateClientUseCaseImpl implements UpdateClientUseCase {

    private final ClientRepositoryPort clientRepositoryPort;

    @Override
    public Optional<Client> execute(Long id, Client client) {
        Optional<Client> existingClient = clientRepositoryPort.findById(id);

        if (existingClient.isPresent()) {
            client.setId(id);
            client.setRegisteredAt(existingClient.get().getRegisteredAt());
            client.setActive(existingClient.get().getActive());

            Client updatedClient = clientRepositoryPort.save(client);

            log.info("Cliente actualizado correctamente. ID: {}", id);

            return Optional.of(updatedClient);
        }

        log.error("No se pudo actualizar. Cliente no encontrado con ID: {}", id);

        return Optional.empty();
    }
}