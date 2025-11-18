package com.clinic.agenda.application.port.in;

import com.clinic.agenda.api.dto.PacienteDto;
import java.util.List;

public interface ListPacientesUseCase {
    List<PacienteDto> listar();
}
