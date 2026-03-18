package com.maria.logistica.infrastructure.output.persistence.adapter;

import com.maria.logistica.domain.model.Client;
import com.maria.logistica.domain.port.output.ClientRepositoryPort;
import com.maria.logistica.infrastructure.output.persistence.mapper.ClientMapper;
import com.maria.logistica.infrastructure.output.persistence.repository.SpringDataClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClientRepositoryAdapter implements ClientRepositoryPort {

    private final SpringDataClientRepository repository;
    private final ClientMapper mapper;

    @Override
    public Client save(Client client) {
        return mapper.toDomain(repository.save(mapper.toEntity(client)));
    }

    @Override
    public List<Client> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Client> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Client> findByEmail(String email) {
        return repository.findByEmail(email).map(mapper::toDomain);
    }
}