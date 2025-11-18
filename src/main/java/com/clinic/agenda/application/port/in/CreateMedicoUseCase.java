package com.clinic.agenda.application.port.in;

import com.clinic.agenda.api.dto.MedicoDto;
import java.util.UUID;

public interface CreateMedicoUseCase {
    UUID crear(MedicoDto dto);
}