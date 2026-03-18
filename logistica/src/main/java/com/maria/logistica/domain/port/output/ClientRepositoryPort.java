package com.maria.logistica.domain.port.output;

import com.maria.logistica.domain.model.Client;
import java.util.List;
import java.util.Optional;

public interface ClientRepositoryPort {
    Client save(Client client);
    List<Client> findAll();
    Optional<Client> findById(Long id);
    Optional<Client> findByEmail(String email);
}