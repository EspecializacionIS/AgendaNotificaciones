package com.clinic.agenda.application.port.out.paciente;

import com.clinic.agenda.domain.model.Paciente;

public interface SavePacientePort {
    Paciente save(Paciente paciente);
}
