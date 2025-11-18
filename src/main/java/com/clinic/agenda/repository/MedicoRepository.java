package com.clinic.agenda.repository;

import com.clinic.agenda.domain.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface MedicoRepository extends JpaRepository<Medico, UUID> {}
