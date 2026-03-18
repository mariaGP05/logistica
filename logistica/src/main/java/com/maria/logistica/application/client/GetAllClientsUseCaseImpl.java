package com.maria.logistica.application.client;

import com.maria.logistica.domain.model.Client;
import com.maria.logistica.domain.port.input.GetAllClientsUseCase;
import com.maria.logistica.domain.port.output.ClientRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllClientsUseCaseImpl implements GetAllClientsUseCase {

    private final ClientRepositoryPort clientRepositoryPort;

    @Override
    public List<Client> execute() {
        return clientRepositoryPort.findAll().stream()
                .filter(client -> Boolean.TRUE.equals(client.getActive()))
                .sorted(Comparator.comparing(Client::getLastName)
                        .thenComparing(Client::getFirstName))
                .toList();
    }
}