package com.maria.logistica.application.client;

import com.maria.logistica.domain.model.Client;
import com.maria.logistica.domain.port.output.ClientRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllClientsUseCaseImplTest {

    @Mock
    private ClientRepositoryPort clientRepositoryPort;

    @InjectMocks
    private GetAllClientsUseCaseImpl getAllClientsUseCase;

    @Test
    void shouldReturnOnlyActiveClientsSortedByLastNameAndFirstName() {
        Client c1 = Client.builder().id(1L).firstName("Carlos").lastName("Zulueta").active(true).build();
        Client c2 = Client.builder().id(2L).firstName("Ana").lastName("Alonso").active(true).build();
        Client c3 = Client.builder().id(3L).firstName("Bea").lastName("Medina").active(false).build();
        Client c4 = Client.builder().id(4L).firstName("Aaron").lastName("Alonso").active(true).build();

        when(clientRepositoryPort.findAll()).thenReturn(List.of(c1, c2, c3, c4));

        List<Client> result = getAllClientsUseCase.execute();

        assertEquals(3, result.size());
        assertEquals(4L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());
        assertEquals(1L, result.get(2).getId());
    }
}