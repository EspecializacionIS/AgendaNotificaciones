package com.clinic.agenda.application.port.in;

import com.clinic.agenda.api.dto.PacienteDto;
import java.util.UUID;

public interface UpdatePacienteUseCase {
    void actualizar(UUID id, PacienteDto dto);
}