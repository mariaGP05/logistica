package com.maria.logistica.application.client;

import com.maria.logistica.domain.model.Client;
import com.maria.logistica.domain.port.output.ClientRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetClientByEmailUseCaseImplTest {

    @Mock
    private ClientRepositoryPort clientRepositoryPort;

    @InjectMocks
    private GetClientByEmailUseCaseImpl getClientByEmailUseCase;

    @Test
    void shouldReturnClientByEmail() {
        Client client = Client.builder().id(1L).email("maria@test.com").build();

        when(clientRepositoryPort.findByEmail("maria@test.com")).thenReturn(Optional.of(client));

        Optional<Client> result = getClientByEmailUseCase.execute("maria@test.com");

        assertTrue(result.isPresent());
        assertEquals("maria@test.com", result.get().getEmail());
    }

    @Test
    void shouldReturnEmptyWhenEmailNotFound() {
        when(clientRepositoryPort.findByEmail("maria@test.com")).thenReturn(Optional.empty());

        Optional<Client> result = getClientByEmailUseCase.execute("maria@test.com");

        assertTrue(result.isEmpty());
    }
}