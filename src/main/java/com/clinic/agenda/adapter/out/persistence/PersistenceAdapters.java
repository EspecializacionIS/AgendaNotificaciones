package com.clinic.agenda.adapter.out.persistence;

import com.clinic.agenda.application.port.out.medico.*;
import com.clinic.agenda.application.port.out.paciente.*;
import com.clinic.agenda.application.port.out.agenda.*;
import com.clinic.agenda.domain.model.*;
import com.clinic.agenda.repository.*;
import org.springframework.stereotype.Component;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.*;

// -------- MEDICO --------
@Component
class MedicoPersistenceAdapter implements SaveMedicoPort, LoadMedicoPort {
    private final MedicoRepository repo;
    MedicoPersistenceAdapter(MedicoRepository repo){ this.repo = repo; }

    @Override public Medico save(Medico m) { return repo.save(m); }
    @Override public Optional<Medico> findById(UUID id){ return repo.findById(id); }
    @Override public List<Medico> findAll(){ return repo.findAll(); }
}

// -------- PACIENTE --------
@Component
class PacientePersistenceAdapter implements SavePacientePort, LoadPacientePort {
    private final PacienteRepository repo;
    PacientePersistenceAdapter(PacienteRepository repo){ this.repo = repo; }

    @Override public Paciente save(Paciente p){ return repo.save(p); }
    @Override public Optional<Paciente> findById(UUID id){ return repo.findById(id); }
    @Override public Optional<Paciente> findByNumeroIdentificacion(String n){ return repo.findByNumeroIdentificacion(n); }
    @Override public List<Paciente> findAll(){ return repo.findAll(); }
}

// -------- AGENDA --------
@Component
class AgendaPersistenceAdapter implements SaveAgendaPort, CheckOverlapPort, LoadAgendaPort {

    private final AgendaRepository repo;

    @PersistenceContext
    private EntityManager em;

    AgendaPersistenceAdapter(AgendaRepository repo){
        this.repo = repo;
    }

    @Override
    public Agenda save(Agenda a){
        return repo.save(a);
    }

    @Override
    public boolean existsOverlap(UUID medicoId, OffsetDateTime inicio, OffsetDateTime fin) {
        return repo.existsOverlap(medicoId, inicio, fin);
    }

    @Override
    public Optional<Agenda> findById(UUID id) {
        return repo.findById(id);
    }

    @Override
    public List<Agenda> findByFiltros(UUID medicoId, LocalDate fecha, AgendaEstado estado) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Agenda> cq = cb.createQuery(Agenda.class);
        Root<Agenda> root = cq.from(Agenda.class);

        List<Predicate> predicates = new ArrayList<>();

        if (medicoId != null) {
            predicates.add(cb.equal(root.get("medico").get("id"), medicoId));
        }

        if (fecha != null) {
            ZoneId zone = ZoneId.of("America/Bogota");
            ZonedDateTime zdtInicio = fecha.atStartOfDay(zone);
           OffsetDateTime inicioDia = fecha.atStartOfDay().atOffset(ZoneOffset.UTC);
        OffsetDateTime finDia = inicioDia.plusDays(1);

        predicates.add(cb.greaterThanOrEqualTo(root.get("inicio"), inicioDia));
        predicates.add(cb.lessThan(root.get("inicio"), finDia));
        }

        if (estado != null) {
            predicates.add(cb.equal(root.get("estado"), estado));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(cq).getResultList();
    }
}