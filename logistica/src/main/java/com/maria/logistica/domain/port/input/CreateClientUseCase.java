package com.maria.logistica.domain.port.input;

import com.maria.logistica.domain.model.Client;

public interface CreateClientUseCase {
    Client execute(Client client);
}