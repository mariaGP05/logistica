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
class GetClientByIdUseCaseImplTest {

    @Mock
    private ClientRepositoryPort clientRepositoryPort;

    @InjectMocks
    private GetClientByIdUseCaseImpl getClientByIdUseCase;

    @Test
    void shouldReturnClientById() {
        Client client = Client.builder().id(1L).firstName("Maria").build();

        when(clientRepositoryPort.findById(1L)).thenReturn(Optional.of(client));

        Optional<Client> result = getClientByIdUseCase.execute(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void shouldReturnEmptyWhenClientNotFound() {
        when(clientRepositoryPort.findById(1L)).thenReturn(Optional.empty());

        Optional<Client> result = getClientByIdUseCase.execute(1L);

        assertTrue(result.isEmpty());
    }
}