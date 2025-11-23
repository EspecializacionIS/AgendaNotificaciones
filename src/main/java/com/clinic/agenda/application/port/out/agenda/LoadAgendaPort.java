package com.clinic.agenda.application.port.out.agenda;

import com.clinic.agenda.domain.model.Agenda;
import com.clinic.agenda.domain.model.AgendaEstado;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoadAgendaPort {

    Optional<Agenda> findById(UUID id);

    List<Agenda> findByFiltros(UUID medicoId, LocalDate fecha, AgendaEstado estado);
}