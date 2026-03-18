package com.maria.logistica.application.service;

import com.maria.logistica.domain.port.input.DeleteLogisticServiceUseCase;
import com.maria.logistica.domain.port.output.LogisticServiceRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteLogisticServiceUseCaseImpl implements DeleteLogisticServiceUseCase {

    private final LogisticServiceRepositoryPort logisticServiceRepositoryPort;

    @Override
    public boolean execute(Long id) {
        if (logisticServiceRepositoryPort.findById(id).isPresent()) {
            logisticServiceRepositoryPort.deleteById(id);

            log.info("Servicio logístico eliminado correctamente. ID: {}", id);

            return true;
        }

        log.error("No se pudo eliminar el servicio. ID no encontrado: {}", id);

        return false;
    }
}