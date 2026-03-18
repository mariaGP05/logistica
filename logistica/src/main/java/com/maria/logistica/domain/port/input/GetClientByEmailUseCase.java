package com.maria.logistica.domain.port.input;

import com.maria.logistica.domain.model.Client;
import java.util.Optional;

public interface GetClientByEmailUseCase {
    Optional<Client> execute(String email);
}