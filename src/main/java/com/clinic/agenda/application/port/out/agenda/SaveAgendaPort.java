package com.clinic.agenda.application.port.out.agenda;

import com.clinic.agenda.domain.model.Agenda;

public interface SaveAgendaPort {
    Agenda save(Agenda agenda);
}