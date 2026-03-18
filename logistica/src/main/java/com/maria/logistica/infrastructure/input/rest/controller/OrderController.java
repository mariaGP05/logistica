package com.maria.logistica.infrastructure.input.rest.controller;

import com.maria.logistica.domain.model.Order;
import com.maria.logistica.domain.model.OrderSummary;
import com.maria.logistica.domain.model.ServiceType;
import com.maria.logistica.domain.port.input.CancelOrderUseCase;
import com.maria.logistica.domain.port.input.CreateOrderCommand;
import com.maria.logistica.domain.port.input.CreateOrderUseCase;
import com.maria.logistica.domain.port.input.GetOrderByIdUseCase;
import com.maria.logistica.domain.port.input.GetOrderSummaryUseCase;
import com.maria.logistica.domain.port.input.GetOrdersByClientUseCase;
import com.maria.logistica.domain.port.input.GetOrdersByDateRangeUseCase;
import com.maria.logistica.domain.port.input.GetOrdersByServiceTypeUseCase;
import com.maria.logistica.domain.port.input.GetTotalSpentByClientUseCase;
import com.maria.logistica.domain.port.input.UpdateOrderStatusUseCase;
import com.maria.logistica.infrastructure.input.rest.dto.CreateOrderRequestDTO;
import com.maria.logistica.infrastructure.input.rest.dto.OrderResponseDTO;
import com.maria.logistica.infrastructure.input.rest.dto.UpdateOrderStatusRequestDTO;
import com.maria.logistica.infrastructure.output.persistence.mapper.OrderMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final GetOrderByIdUseCase getOrderByIdUseCase;
    private final UpdateOrderStatusUseCase updateOrderStatusUseCase;
    private final CancelOrderUseCase cancelOrderUseCase;
    private final GetOrdersByClientUseCase getOrdersByClientUseCase;
    private final GetOrdersByServiceTypeUseCase getOrdersByServiceTypeUseCase;
    private final GetOrdersByDateRangeUseCase getOrdersByDateRangeUseCase;
    private final GetTotalSpentByClientUseCase getTotalSpentByClientUseCase;
    private final GetOrderSummaryUseCase getOrderSummaryUseCase;
    private final OrderMapper orderMapper;

    @PostMapping("/orders")
    public ResponseEntity<OrderResponseDTO> create(@Valid @RequestBody CreateOrderRequestDTO requestDTO) {
        CreateOrderCommand command = CreateOrderCommand.builder()
                .clientId(requestDTO.getClientId())
                .serviceId(requestDTO.getServiceId())
                .originCity(requestDTO.getOriginCity())
                .destinationCity(requestDTO.getDestinationCity())
                .distanceKm(requestDTO.getDistanceKm())
                .notes(requestDTO.getNotes())
                .build();

        Order created = createOrderUseCase.execute(command);

        return ResponseEntity
                .created(URI.create("/api/v1/orders/" + created.getId()))
                .body(orderMapper.toResponseDto(created));
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderResponseDTO> getById(@PathVariable Long id) {
        return getOrderByIdUseCase.execute(id)
                .map(orderMapper::toResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/orders/{id}/status")
    public ResponseEntity<OrderResponseDTO> updateStatus(@PathVariable Long id,
                                                         @Valid @RequestBody UpdateOrderStatusRequestDTO requestDTO) {
        return updateOrderStatusUseCase.execute(id, requestDTO.getStatus())
                .map(orderMapper::toResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        boolean cancelled = cancelOrderUseCase.execute(id);

        if (cancelled) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/clients/{id}/orders")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByClient(@PathVariable Long id) {
        List<OrderResponseDTO> orders = getOrdersByClientUseCase.execute(id).stream()
                .map(orderMapper::toResponseDto)
                .toList();

        if (orders.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(orders);
    }

    @GetMapping("/clients/{id}/orders/total-spent")
    public ResponseEntity<BigDecimal> getTotalSpent(@PathVariable Long id) {
        return ResponseEntity.ok(getTotalSpentByClientUseCase.execute(id));
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersFiltered(
            @RequestParam(required = false) ServiceType type,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {

        List<Order> orders;

        if (type != null) {
            orders = getOrdersByServiceTypeUseCase.execute(type);
        } else if (from != null && to != null) {
            orders = getOrdersByDateRangeUseCase.execute(from.atStartOfDay(), to.atTime(23, 59, 59));
        } else {
            orders = List.of();
        }

        List<OrderResponseDTO> response = orders.stream()
                .map(orderMapper::toResponseDto)
                .toList();

        if (response.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/orders/summary")
    public ResponseEntity<OrderSummary> getSummary() {
        return ResponseEntity.ok(getOrderSummaryUseCase.execute());
    }
}