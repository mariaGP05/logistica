package com.maria.logistica.infrastructure.input.rest.controller;

import com.maria.logistica.domain.model.LogisticService;
import com.maria.logistica.domain.model.ServiceType;
import com.maria.logistica.domain.port.input.CreateLogisticServiceUseCase;
import com.maria.logistica.domain.port.input.DeleteLogisticServiceUseCase;
import com.maria.logistica.domain.port.input.GetAllServicesUseCase;
import com.maria.logistica.domain.port.input.GetServiceByIdUseCase;
import com.maria.logistica.domain.port.input.GetServicesByTypeUseCase;
import com.maria.logistica.domain.port.input.UpdateLogisticServiceUseCase;
import com.maria.logistica.infrastructure.input.rest.dto.LogisticServiceRequestDTO;
import com.maria.logistica.infrastructure.input.rest.dto.LogisticServiceResponseDTO;
import com.maria.logistica.infrastructure.output.persistence.mapper.LogisticServiceMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/services")
@RequiredArgsConstructor
public class LogisticServiceController {

    private final CreateLogisticServiceUseCase createLogisticServiceUseCase;
    private final GetAllServicesUseCase getAllServicesUseCase;
    private final GetServiceByIdUseCase getServiceByIdUseCase;
    private final GetServicesByTypeUseCase getServicesByTypeUseCase;
    private final UpdateLogisticServiceUseCase updateLogisticServiceUseCase;
    private final DeleteLogisticServiceUseCase deleteLogisticServiceUseCase;
    private final LogisticServiceMapper logisticServiceMapper;

    @GetMapping
    public ResponseEntity<List<LogisticServiceResponseDTO>> getAll(
            @RequestParam(required = false) ServiceType type) {

        List<LogisticService> services = (type != null)
                ? getServicesByTypeUseCase.execute(type)
                : getAllServicesUseCase.execute();

        List<LogisticServiceResponseDTO> response = services.stream()
                .map(logisticServiceMapper::toResponseDto)
                .toList();

        if (response.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LogisticServiceResponseDTO> getById(@PathVariable Long id) {
        return getServiceByIdUseCase.execute(id)
                .map(logisticServiceMapper::toResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<LogisticServiceResponseDTO> create(
            @Valid @RequestBody LogisticServiceRequestDTO requestDTO) {

        LogisticService service = logisticServiceMapper.toDomain(requestDTO);
        LogisticService created = createLogisticServiceUseCase.execute(service);

        return ResponseEntity
                .created(URI.create("/api/v1/services/" + created.getId()))
                .body(logisticServiceMapper.toResponseDto(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LogisticServiceResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody LogisticServiceRequestDTO requestDTO) {

        LogisticService service = logisticServiceMapper.toDomain(requestDTO);

        return updateLogisticServiceUseCase.execute(id, service)
                .map(logisticServiceMapper::toResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = deleteLogisticServiceUseCase.execute(id);

        if (deleted) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}