package com.clinic.agenda.application.port.out.medico;

import com.clinic.agenda.domain.model.Medico;

public interface SaveMedicoPort {
    Medico save(Medico medico);
}