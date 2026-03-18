package com.maria.logistica.application.order;

import com.maria.logistica.domain.model.Client;
import com.maria.logistica.domain.model.LogisticService;
import com.maria.logistica.domain.model.Order;
import com.maria.logistica.domain.model.OrderStatus;
import com.maria.logistica.domain.model.ServiceType;
import com.maria.logistica.domain.port.input.CreateOrderCommand;
import com.maria.logistica.domain.port.output.ClientRepositoryPort;
import com.maria.logistica.domain.port.output.LogisticServiceRepositoryPort;
import com.maria.logistica.domain.port.output.OrderRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateOrderUseCaseImplTest {

    @Mock
    private ClientRepositoryPort clientRepositoryPort;

    @Mock
    private LogisticServiceRepositoryPort logisticServiceRepositoryPort;

    @Mock
    private OrderRepositoryPort orderRepositoryPort;

    @InjectMocks
    private CreateOrderUseCaseImpl createOrderUseCase;

    @Test
    void shouldCalculateCostCorrectly_forStandardService() {
        assertCost(ServiceType.STANDARD, "310.00");
    }

    @Test
    void shouldCalculateCostCorrectly_forExpressService() {
        assertCost(ServiceType.EXPRESS, "558.00");
    }

    @Test
    void shouldCalculateCostCorrectly_forRefrigeratedService() {
        assertCost(ServiceType.REFRIGERATED, "682.00");
    }

    @Test
    void shouldCalculateCostCorrectly_forHeavyService() {
        assertCost(ServiceType.HEAVY, "465.00");
    }

    @Test
    void shouldCalculateCostCorrectly_forDocumentService() {
        assertCost(ServiceType.DOCUMENT, "217.00");
    }

    @Test
    void shouldCreateOrderWithPendingStatus() {
        Client client = Client.builder().id(1L).build();
        LogisticService service = LogisticService.builder()
                .id(2L)
                .type(ServiceType.STANDARD)
                .pricePerKm(new BigDecimal("0.50"))
                .build();

        CreateOrderCommand command = CreateOrderCommand.builder()
                .clientId(1L)
                .serviceId(2L)
                .originCity("Murcia")
                .destinationCity("Valencia")
                .distanceKm(100.0)
                .notes("Test")
                .build();

        when(clientRepositoryPort.findById(1L)).thenReturn(Optional.of(client));
        when(logisticServiceRepositoryPort.findById(2L)).thenReturn(Optional.of(service));
        when(orderRepositoryPort.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Order result = createOrderUseCase.execute(command);

        assertEquals(OrderStatus.PENDING, result.getStatus());
        assertNotNull(result.getCreatedAt());
    }

    @Test
    void shouldThrowExceptionWhenClientNotFound() {
        CreateOrderCommand command = CreateOrderCommand.builder()
                .clientId(1L)
                .serviceId(2L)
                .originCity("Murcia")
                .destinationCity("Valencia")
                .distanceKm(100.0)
                .notes("Test")
                .build();

        when(clientRepositoryPort.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> createOrderUseCase.execute(command)
        );

        assertTrue(exception.getMessage().contains("Cliente no encontrado"));
    }

    @Test
    void shouldThrowExceptionWhenServiceNotFound() {
        Client client = Client.builder().id(1L).build();

        CreateOrderCommand command = CreateOrderCommand.builder()
                .clientId(1L)
                .serviceId(2L)
                .originCity("Murcia")
                .destinationCity("Valencia")
                .distanceKm(100.0)
                .notes("Test")
                .build();

        when(clientRepositoryPort.findById(1L)).thenReturn(Optional.of(client));
        when(logisticServiceRepositoryPort.findById(2L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> createOrderUseCase.execute(command)
        );

        assertTrue(exception.getMessage().contains("Servicio no encontrado"));
    }

    private void assertCost(ServiceType type, String expectedCost) {
        Client client = Client.builder()
                .id(1L)
                .firstName("Maria")
                .lastName("Garcia")
                .email("maria@test.com")
                .active(true)
                .build();

        LogisticService service = LogisticService.builder()
                .id(2L)
                .name("Servicio " + type.name())
                .type(type)
                .pricePerKm(new BigDecimal("0.50"))
                .available(true)
                .build();

        CreateOrderCommand command = CreateOrderCommand.builder()
                .clientId(1L)
                .serviceId(2L)
                .originCity("Murcia")
                .destinationCity("Valencia")
                .distanceKm(620.0)
                .notes("Pedido de prueba")
                .build();

        when(clientRepositoryPort.findById(1L)).thenReturn(Optional.of(client));
        when(logisticServiceRepositoryPort.findById(2L)).thenReturn(Optional.of(service));
        when(orderRepositoryPort.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Order result = createOrderUseCase.execute(command);

        assertEquals(new BigDecimal(expectedCost), result.getTotalCost());
    }
}