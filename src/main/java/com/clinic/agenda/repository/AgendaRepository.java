package com.clinic.agenda.repository;



import com.clinic.agenda.domain.model.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface AgendaRepository extends JpaRepository<Agenda, UUID> {

    @Query("""
        select count(a)>0 from Agenda a
        where a.medico.id = :medicoId
          and ( (a.inicio < :fin) and (a.fin > :inicio) )
    """)
    boolean existsOverlap(UUID medicoId, OffsetDateTime inicio, OffsetDateTime fin);
}