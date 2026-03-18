package com.maria.logistica.infrastructure.input.rest.controller;

import com.maria.logistica.domain.model.Client;
import com.maria.logistica.domain.port.input.CreateClientUseCase;
import com.maria.logistica.domain.port.input.DeleteClientUseCase;
import com.maria.logistica.domain.port.input.GetAllClientsUseCase;
import com.maria.logistica.domain.port.input.GetClientByEmailUseCase;
import com.maria.logistica.domain.port.input.GetClientByIdUseCase;
import com.maria.logistica.domain.port.input.UpdateClientUseCase;
import com.maria.logistica.infrastructure.input.rest.dto.ClientRequestDTO;
import com.maria.logistica.infrastructure.input.rest.dto.ClientResponseDTO;
import com.maria.logistica.infrastructure.output.persistence.mapper.ClientMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
public class ClientController {

    private final CreateClientUseCase createClientUseCase;
    private final GetAllClientsUseCase getAllClientsUseCase;
    private final GetClientByEmailUseCase getClientByEmailUseCase;
    private final GetClientByIdUseCase getClientByIdUseCase;
    private final UpdateClientUseCase updateClientUseCase;
    private final DeleteClientUseCase deleteClientUseCase;
    private final ClientMapper clientMapper;

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(required = false) String email) {

        if (email != null && !email.isBlank()) {
            Optional<Client> client = getClientByEmailUseCase.execute(email);

            if (client.isPresent()) {
                return ResponseEntity.ok(clientMapper.toResponseDto(client.get()));
            }

            return ResponseEntity.notFound().build();
        }

        List<ClientResponseDTO> clients = getAllClientsUseCase.execute().stream()
                .map(clientMapper::toResponseDto)
                .toList();

        if (clients.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> getById(@PathVariable Long id) {
        return getClientByIdUseCase.execute(id)
                .map(clientMapper::toResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ClientResponseDTO> create(@Valid @RequestBody ClientRequestDTO requestDTO) {
        Client client = clientMapper.toDomain(requestDTO);
        Client created = createClientUseCase.execute(client);

        return ResponseEntity
                .created(URI.create("/api/v1/clients/" + created.getId()))
                .body(clientMapper.toResponseDto(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> update(@PathVariable Long id,
                                                    @Valid @RequestBody ClientRequestDTO requestDTO) {
        Client client = clientMapper.toDomain(requestDTO);

        return updateClientUseCase.execute(id, client)
                .map(clientMapper::toResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = deleteClientUseCase.execute(id);

        if (deleted) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}