package com.clinic.agenda.api.dto;

import jakarta.validation.constraints.NotBlank;

public record ChangeAgendaStatusRequest(
        @NotBlank
        String nuevoEstado   // "PROGRAMADA", "ATENDIDA", "CANCELADA", "NO_ASISTIO"
) {}