package com.clinic.agenda.application.port.out.medico;

import com.clinic.agenda.domain.model.Medico;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoadMedicoPort {
    Optional<Medico> findById(UUID id);
    List<Medico> findAll();
}