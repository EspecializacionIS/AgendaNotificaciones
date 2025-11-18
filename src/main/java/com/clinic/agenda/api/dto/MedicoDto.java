package com.clinic.agenda.api.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalTime;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MedicoDto {
    private UUID id;
    @NotBlank @Size(max=160) private String nombreCompleto;
    @Size(max=120) private String especialidad;
    @Email @NotBlank private String email;
    @Size(max=40) private String telefono;
    @NotNull private LocalTime jornadaInicio;
    @NotNull private LocalTime jornadaFin;
    private Boolean activo;
}