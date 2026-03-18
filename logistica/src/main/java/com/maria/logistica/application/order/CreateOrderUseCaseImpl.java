package com.maria.logistica.application.order;

import com.maria.logistica.domain.model.Client;
import com.maria.logistica.domain.model.LogisticService;
import com.maria.logistica.domain.model.Order;
import com.maria.logistica.domain.model.OrderStatus;
import com.maria.logistica.domain.port.input.CreateOrderCommand;
import com.maria.logistica.domain.port.input.CreateOrderUseCase;
import com.maria.logistica.domain.port.output.ClientRepositoryPort;
import com.maria.logistica.domain.port.output.LogisticServiceRepositoryPort;
import com.maria.logistica.domain.port.output.OrderRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateOrderUseCaseImpl implements CreateOrderUseCase {

    private final ClientRepositoryPort clientRepositoryPort;
    private final LogisticServiceRepositoryPort logisticServiceRepositoryPort;
    private final OrderRepositoryPort orderRepositoryPort;

    @Override
    public Order execute(CreateOrderCommand cmd) {
        Client client = clientRepositoryPort.findById(cmd.getClientId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + cmd.getClientId()));

        LogisticService service = logisticServiceRepositoryPort.findById(cmd.getServiceId())
                .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado con ID: " + cmd.getServiceId()));

        BigDecimal distance = BigDecimal.valueOf(cmd.getDistanceKm());
        BigDecimal multiplier = BigDecimal.valueOf(service.getType().getMultiplier());

        BigDecimal totalCost = service.getPricePerKm()
                .multiply(distance)
                .multiply(multiplier)
                .setScale(2, RoundingMode.HALF_UP);

        Order order = Order.builder()
                .client(client)
                .service(service)
                .originCity(cmd.getOriginCity())
                .destinationCity(cmd.getDestinationCity())
                .distanceKm(cmd.getDistanceKm())
                .totalCost(totalCost)
                .status(OrderStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .notes(cmd.getNotes())
                .build();

        Order saved = orderRepositoryPort.save(order);

        log.info("Pedido creado correctamente. ID: {}, Coste total: {}", saved.getId(), saved.getTotalCost());

        return saved;
    }
}