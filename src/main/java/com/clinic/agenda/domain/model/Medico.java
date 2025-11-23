package com.clinic.agenda.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalTime;
import java.util.UUID;

@Entity @Table(name = "medicos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Medico {
    @Id private UUID id;

    @Column(name="nombre_completo", nullable=false, length=160)
    private String nombreCompleto;

    private String especialidad;

    @Column(nullable=false, unique=true, length=160)
    private String email;

    private String telefono;

    @Column(name="jornada_inicio", nullable=false)
    private LocalTime jornadaInicio;

    @Column(name="jornada_fin", nullable=false)
    private LocalTime jornadaFin;

    @Column(nullable=false)
    private Boolean activo = true;

    public LocalTime getJornadaInicio() {
    return jornadaInicio;
}

public LocalTime getJornadaFin() {
    return jornadaFin;
}
}
