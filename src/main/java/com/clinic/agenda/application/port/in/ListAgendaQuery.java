package com.clinic.agenda.application.port.in;

import com.clinic.agenda.domain.model.AgendaEstado;

import java.time.LocalDate;
import java.util.UUID;

public record ListAgendaQuery(
        UUID medicoId,
        LocalDate fecha,
        AgendaEstado estado
) {}