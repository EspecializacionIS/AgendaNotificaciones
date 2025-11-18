package com.clinic.agenda.api.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PacienteDto {
    private UUID id;
    @NotBlank @Size(max=40) private String numeroIdentificacion;
    @NotBlank @Size(max=160) private String nombreCompleto;
    @NotNull private LocalDate fechaNacimiento;
    @NotBlank private String genero; // "MASCULINO","FEMENINO","OTRO"
    @Size(max=200) private String direccion;
    @Size(max=40) private String telefono;
    @Email private String email;
    private Boolean activo;
}
