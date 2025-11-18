package com.clinic.agenda.repository;

import com.clinic.agenda.domain.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface PacienteRepository extends JpaRepository<Paciente, UUID> {
    Optional<Paciente> findByNumeroIdentificacion(String numeroIdentificacion);
}