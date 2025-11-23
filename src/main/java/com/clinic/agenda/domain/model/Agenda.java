package com.clinic.agenda.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity @Table(name = "agenda")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Agenda {
    @Id private UUID id;

    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="medico_id")
    private Medico medico;

    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="paciente_id")
    private Paciente paciente;

    @Column(nullable=false)
    private OffsetDateTime inicio;

    @Column(nullable=false)
    private OffsetDateTime fin;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private AgendaEstado estado = AgendaEstado.PROGRAMADA;

    private String motivo;
    private String notas;

public void cambiarEstado(AgendaEstado nuevoEstado) {
        if (this.estado == AgendaEstado.CANCELADA || this.estado == AgendaEstado.NO_ASISTIO) {
            throw new IllegalStateException("No se puede cambiar el estado de una cita cancelada o marcada como no asistida.");
        }

        if (this.estado == AgendaEstado.ATENDIDA && nuevoEstado != AgendaEstado.ATENDIDA) {
            throw new IllegalStateException("Una cita atendida no puede volver a otro estado.");
        }
    this.estado = nuevoEstado;
    }

}
