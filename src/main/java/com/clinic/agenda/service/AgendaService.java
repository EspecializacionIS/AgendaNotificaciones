package com.clinic.agenda.service;

import com.clinic.agenda.api.dto.*;
import com.clinic.agenda.domain.model.*;
import com.clinic.agenda.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;
import java.util.UUID;

@Service
public class AgendaService {
    private final AgendaRepository agendaRepo;
    private final MedicoRepository medicoRepo;
    private final PacienteRepository pacienteRepo;
    private final NotificationService notificationService;

    public AgendaService(AgendaRepository a, MedicoRepository m, PacienteRepository p,
     NotificationService notificationService) {
        this.agendaRepo = a; this.medicoRepo = m; this.pacienteRepo = p;
        this.notificationService = notificationService;
    }

    @Transactional
    public AgendaResponse crear(AgendaCreateRequest req) {
        Medico medico = medicoRepo.findById(req.medicoId())
                .orElseThrow(() -> new EntityNotFoundException("Médico no encontrado"));
        if (Boolean.FALSE.equals(medico.getActivo())) throw new IllegalArgumentException("Médico inactivo");

        Paciente paciente = pacienteRepo.findById(req.pacienteId())
                .orElseThrow(() -> new EntityNotFoundException("Paciente no encontrado"));
        if (Boolean.FALSE.equals(paciente.getActivo())) throw new IllegalArgumentException("Paciente inactivo");

        if (!req.inicio().isBefore(req.fin())) throw new IllegalArgumentException("inicio < fin requerido");

        long mins = Duration.between(req.inicio(), req.fin()).toMinutes();
        if (mins < 15 || mins > 180) throw new IllegalArgumentException("Duración entre 15 y 180 minutos");

        if (req.inicio().isBefore(OffsetDateTime.now().minusMinutes(5)))
            throw new IllegalArgumentException("No se permiten citas en el pasado");

        // Validar jornada del médico (se asume misma zona del servidor)
        LocalTime ini = req.inicio().toLocalTime();
        LocalTime fin = req.fin().toLocalTime();
        if (ini.isBefore(medico.getJornadaInicio()) || fin.isAfter(medico.getJornadaFin()))
            throw new IllegalArgumentException("Fuera del horario del médico");

        boolean overlap = agendaRepo.existsByMedico_IdAndInicioLessThanAndFinGreaterThan(
                medico.getId(), req.fin(), req.inicio());
        if (overlap) throw new IllegalArgumentException("Ya existe una cita en ese horario");

        Agenda ag = Agenda.builder()
                .id(UUID.randomUUID())
                .medico(medico)
                .paciente(paciente)
                .inicio(req.inicio())
                .fin(req.fin())
                .motivo(req.motivo())
                .notas(req.notas())
                .estado(AgendaEstado.PROGRAMADA)
                .build();

        agendaRepo.save(ag);
        notificationService.enviarCorreoCitaCreada(paciente, ag, medico.getNombreCompleto());
        return toResponse(ag);
    }

    @Transactional
    public AgendaResponse actualizarEstado(UUID id, AgendaEstadoUpdateRequest req) {
        Agenda ag = agendaRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cita no encontrada"));
        ag.setEstado(req.estado());
        if (req.notas()!=null && !req.notas().isBlank()) ag.setNotas(req.notas());
        return toResponse(ag);
    }

    @Transactional
    public void cancelar(UUID id, String motivo) {
        Agenda ag = agendaRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cita no encontrada"));
        ag.setEstado(AgendaEstado.CANCELADA);
        if (motivo!=null) ag.setNotas(motivo);
    }

    public List<AgendaResponse> citasMedicoEnDia(UUID medicoId, LocalDate dia) {
        OffsetDateTime desde = dia.atStartOfDay().atOffset(ZoneOffset.UTC);
        OffsetDateTime hasta = dia.plusDays(1).atStartOfDay().atOffset(ZoneOffset.UTC);
        return agendaRepo.findByMedico_IdAndInicioBetweenOrderByInicioAsc(medicoId, desde, hasta)
                .stream().map(this::toResponse).toList();
    }

    private AgendaResponse toResponse(Agenda ag) {
        return new AgendaResponse(
                ag.getId(),
                ag.getMedico().getId(), ag.getMedico().getNombreCompleto(),
                ag.getPaciente().getId(), ag.getPaciente().getNombreCompleto(),
                ag.getInicio(), ag.getFin(), ag.getEstado(), ag.getMotivo(), ag.getNotas()
        );
    }
}