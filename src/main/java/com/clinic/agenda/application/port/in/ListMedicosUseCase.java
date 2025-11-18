package com.clinic.agenda.application.port.in;

import com.clinic.agenda.api.dto.MedicoDto;
import java.util.List;

public interface ListMedicosUseCase {
    List<MedicoDto> listar();
}