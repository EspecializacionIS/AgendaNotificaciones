package com.clinic.agenda.api.dto;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.clinic.agenda.domain.model.AgendaEstado;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AgendaResponse {
    private UUID id;
    private UUID medicoId;
    private UUID pacienteId;
    private OffsetDateTime inicio;
    private OffsetDateTime fin;
    private String motivo;
    private String notas;
    private AgendaEstado estado;
}
