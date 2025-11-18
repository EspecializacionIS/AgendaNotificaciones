package com.clinic.agenda.application.port.out.paciente;

import com.clinic.agenda.domain.model.Paciente;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoadPacientePort {
    Optional<Paciente> findById(UUID id);
    Optional<Paciente> findByNumeroIdentificacion(String numeroIdentificacion);
    List<Paciente> findAll();
}
