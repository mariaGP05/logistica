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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteClientUseCaseImplTest {

    @Mock
    private ClientRepositoryPort clientRepositoryPort;

    @InjectMocks
    private DeleteClientUseCaseImpl deleteClientUseCase;

    @Test
    void shouldDeleteClientLogically() {
        Client client = Client.builder()
                .id(1L)
                .active(true)
                .build();

        when(clientRepositoryPort.findById(1L)).thenReturn(Optional.of(client));
        when(clientRepositoryPort.save(client)).thenReturn(client);

        boolean result = deleteClientUseCase.execute(1L);

        assertTrue(result);
        assertFalse(client.getActive());

        verify(clientRepositoryPort).findById(1L);
        verify(clientRepositoryPort).save(client);
    }

    @Test
    void shouldReturnFalseWhenClientNotFound() {
        when(clientRepositoryPort.findById(1L)).thenReturn(Optional.empty());

        boolean result = deleteClientUseCase.execute(1L);

        assertFalse(result);

        verify(clientRepositoryPort).findById(1L);
        verify(clientRepositoryPort, never()).save(any());
    }
}