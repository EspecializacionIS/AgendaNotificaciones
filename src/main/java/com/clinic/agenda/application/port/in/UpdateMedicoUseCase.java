package com.clinic.agenda.application.port.in;

import com.clinic.agenda.api.dto.MedicoDto;
import java.util.UUID;

public interface UpdateMedicoUseCase {
    void actualizar(UUID id, MedicoDto dto);
}
