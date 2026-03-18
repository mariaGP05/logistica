package com.maria.logistica.application.client;

import com.maria.logistica.domain.model.Client;
import com.maria.logistica.domain.port.output.ClientRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDateTime;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateClientUseCaseImplTest {

    @Mock
    private ClientRepositoryPort clientRepositoryPort;

    @InjectMocks
    private CreateClientUseCaseImpl createClientUseCase;

    @Test
    void shouldCreateClient() {
        Client client = Client.builder()
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
                .registeredAt(LocalDateTime.now())
                .active(true)
                .build();

        when(clientRepositoryPort.save(any(Client.class))).thenReturn(saved);

        Client result = createClientUseCase.execute(client);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertTrue(result.getActive());
        assertNotNull(client.getRegisteredAt());

        verify(clientRepositoryPort).save(client);
    }
}