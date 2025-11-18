package com.clinic.agenda.domain.model;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity @Table(name = "pacientes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Paciente {
    @Id private UUID id;

    @Column(name="numero_identificacion", nullable=false, unique=true, length=40)
    private String numeroIdentificacion;

    @Column(name="nombre_completo", nullable=false, length=160)
    private String nombreCompleto;

    @Column(name="fecha_nacimiento", nullable=false)
    private LocalDate fechaNacimiento;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private Genero genero;

    private String direccion;
    private String telefono;

    @Column(name="email", length=160)
    private String email;

    @Column(nullable=false)
    private Boolean activo = true;
}