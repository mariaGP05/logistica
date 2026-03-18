package com.maria.logistica.domain.port.input;

import com.maria.logistica.domain.model.Client;
import java.util.Optional;

public interface GetClientByIdUseCase {
    Optional<Client> execute(Long id);
}