package com.clinic.agenda.api.dto;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.clinic.agenda.domain.model.AgendaEstado;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AgendaResponse {
    UUID id;
    UUID medicoId;
    UUID pacienteId;
    OffsetDateTime inicio;
    OffsetDateTime fin;
    AgendaEstado estado;
    String motivo;
    String notas;
    
}
