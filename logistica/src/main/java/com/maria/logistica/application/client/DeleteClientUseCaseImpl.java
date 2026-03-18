package com.maria.logistica.application.client;

import com.maria.logistica.domain.model.Client;
import com.maria.logistica.domain.port.input.DeleteClientUseCase;
import com.maria.logistica.domain.port.output.ClientRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteClientUseCaseImpl implements DeleteClientUseCase {

    private final ClientRepositoryPort clientRepositoryPort;

    @Override
    public boolean execute(Long id) {
        Optional<Client> clientOpt = clientRepositoryPort.findById(id);

        if (clientOpt.isPresent()) {
            Client client = clientOpt.get();
            client.setActive(false);
            clientRepositoryPort.save(client);

            log.info("Cliente dado de baja lógicamente. ID: {}", id);
            return true;
        }

        log.error("No se encontró el cliente con ID: {}", id);
        return false;
    }
}