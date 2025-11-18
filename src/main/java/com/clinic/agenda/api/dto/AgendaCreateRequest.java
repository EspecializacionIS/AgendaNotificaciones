package com.clinic.agenda.api.dto;

import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;
import java.util.UUID;

public class AgendaCreateRequest {

    @NotNull
    private UUID medicoId;

    @NotNull
    private UUID pacienteId;

    @NotNull
    private OffsetDateTime inicio;

    @NotNull
    private OffsetDateTime fin;

    private String motivo;
    private String notas;

    public UUID getMedicoId() { return medicoId; }
    public void setMedicoId(UUID medicoId) { this.medicoId = medicoId; }

    public UUID getPacienteId() { return pacienteId; }
    public void setPacienteId(UUID pacienteId) { this.pacienteId = pacienteId; }

    public OffsetDateTime getInicio() { return inicio; }
    public void setInicio(OffsetDateTime inicio) { this.inicio = inicio; }

    public OffsetDateTime getFin() { return fin; }
    public void setFin(OffsetDateTime fin) { this.fin = fin; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }
}