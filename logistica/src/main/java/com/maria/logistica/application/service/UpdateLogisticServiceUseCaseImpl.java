package com.maria.logistica.application.service;

import com.maria.logistica.domain.model.LogisticService;
import com.maria.logistica.domain.port.input.UpdateLogisticServiceUseCase;
import com.maria.logistica.domain.port.output.LogisticServiceRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateLogisticServiceUseCaseImpl implements UpdateLogisticServiceUseCase {

    private final LogisticServiceRepositoryPort logisticServiceRepositoryPort;

    @Override
    public Optional<LogisticService> execute(Long id, LogisticService service) {
        Optional<LogisticService> existingService = logisticServiceRepositoryPort.findById(id);

        if (existingService.isPresent()) {
            service.setId(id);

            if (service.getAvailable() == null) {
                service.setAvailable(existingService.get().getAvailable());
            }

            LogisticService updatedService = logisticServiceRepositoryPort.save(service);

            log.info("Servicio logístico actualizado correctamente. ID: {}", id);

            return Optional.of(updatedService);
        }

        log.error("No se pudo actualizar. Servicio no encontrado con ID: {}", id);

        return Optional.empty();
    }
}