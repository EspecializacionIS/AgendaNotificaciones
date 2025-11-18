package com.clinic.agenda.adapter.out.persistence;

import com.clinic.agenda.application.port.out.medico.*;
import com.clinic.agenda.application.port.out.paciente.*;
import com.clinic.agenda.application.port.out.agenda.*;
import com.clinic.agenda.domain.model.*;
import com.clinic.agenda.repository.*;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.*;

@Component
class MedicoPersistenceAdapter implements SaveMedicoPort, LoadMedicoPort {
    private final MedicoRepository repo;
    MedicoPersistenceAdapter(MedicoRepository repo){ this.repo = repo; }

    @Override public Medico save(Medico m) { return repo.save(m); }
    @Override public Optional<Medico> findById(UUID id){ return repo.findById(id); }
    @Override public List<Medico> findAll(){ return repo.findAll(); }
}

@Component
class PacientePersistenceAdapter implements SavePacientePort, LoadPacientePort {
    private final PacienteRepository repo;
    PacientePersistenceAdapter(PacienteRepository repo){ this.repo = repo; }

    @Override public Paciente save(Paciente p){ return repo.save(p); }
    @Override public Optional<Paciente> findById(UUID id){ return repo.findById(id); }
    @Override public Optional<Paciente> findByNumeroIdentificacion(String n){ return repo.findByNumeroIdentificacion(n); }
    @Override public List<Paciente> findAll(){ return repo.findAll(); }
}

@Component
class AgendaPersistenceAdapter implements SaveAgendaPort, CheckOverlapPort {
    private final AgendaRepository repo;
    AgendaPersistenceAdapter(AgendaRepository repo){ this.repo = repo; }

    @Override public Agenda save(Agenda a){ return repo.save(a); }

    @Override
    public boolean existsOverlap(UUID medicoId, OffsetDateTime inicio, OffsetDateTime fin) {
        return repo.existsOverlap(medicoId, inicio, fin);
    }
}