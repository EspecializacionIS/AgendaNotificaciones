package com.clinic.agenda.application.port.in;

import com.clinic.agenda.domain.model.Agenda;

import java.util.List;

public interface ListAgendaUseCase {

    List<Agenda> listar(ListAgendaQuery query);
}