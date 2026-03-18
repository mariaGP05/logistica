package com.maria.logistica.application.client;

import com.maria.logistica.domain.model.Client;
import com.maria.logistica.domain.port.output.ClientRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDateTime;
import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateClientUseCaseImplTest {

    @Mock
    private ClientRepositoryPort clientRepositoryPort;

    @InjectMocks
    private UpdateClientUseCaseImpl updateClientUseCase;

    @Test
    void shouldUpdateClient() {
        Client existing = Client.builder()
                .id(1L)
                .registeredAt(LocalDateTime.now())
                .active(true)
                .build();

        Client updateData = Client.builder()
                .firstName("Maria")
                .lastName("Garcia")
                .email("maria@test.com")
                .phone("672918374")
                .address("Murcia")
                .build();

        Client saved = Client.builder()
                .id(1L)
                .firstName("Maria")
                .lastName("Garcia")
                .email("maria@test.com")
                .phone("672918374")
                .address("Murcia")
                .registeredAt(existing.getRegisteredAt())
                .active(true)
                .build();

        when(clientRepositoryPort.findById(1L)).thenReturn(Optional.of(existing));
        when(clientRepositoryPort.save(any(Client.class))).thenReturn(saved);

        Optional<Client> result = updateClientUseCase.execute(1L, updateData);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        assertEquals("Maria", result.get().getFirstName());

        verify(clientRepositoryPort).findById(1L);
        verify(clientRepositoryPort).save(any(Client.class));
    }

    @Test
    void shouldReturnEmptyWhenClientNotFound() {
        Client updateData = Client.builder().firstName("Maria").build();

        when(clientRepositoryPort.findById(1L)).thenReturn(Optional.empty());

        Optional<Client> result = updateClientUseCase.execute(1L, updateData);

        assertTrue(result.isEmpty());

        verify(clientRepositoryPort).findById(1L);
        verify(clientRepositoryPort, never()).save(any());
    }
}