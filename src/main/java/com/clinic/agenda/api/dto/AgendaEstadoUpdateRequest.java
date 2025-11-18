package com.clinic.agenda.api.dto;

import com.clinic.agenda.domain.model.AgendaEstado;
import jakarta.validation.constraints.NotNull;

public record AgendaEstadoUpdateRequest(@NotNull AgendaEstado estado, String notas) {}