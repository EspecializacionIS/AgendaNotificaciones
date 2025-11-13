package com.clinic.agenda.api.dto;

import com.clinic.agenda.domain.model.AgendaEstado;
import java.time.OffsetDateTime;
import java.util.UUID;

public record AgendaResponse(
        UUID id,
        UUID medicoId,
        String medicoNombre,
        UUID pacienteId,
        String pacienteNombre,
        OffsetDateTime inicio,
        OffsetDateTime fin,
        AgendaEstado estado,
        String motivo,
        String notas
) {}
