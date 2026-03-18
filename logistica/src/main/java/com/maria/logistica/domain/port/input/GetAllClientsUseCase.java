package com.maria.logistica.domain.port.input;

import com.maria.logistica.domain.model.Client;
import java.util.List;

public interface GetAllClientsUseCase {
    List<Client> execute();
}