package com.clinic.agenda.application.port.in;

import com.clinic.agenda.domain.model.AgendaEstado;

import java.util.UUID;

public interface ChangeAgendaStatusUseCase {

    void cambiarEstado(UUID agendaId, AgendaEstado nuevoEstado);
}