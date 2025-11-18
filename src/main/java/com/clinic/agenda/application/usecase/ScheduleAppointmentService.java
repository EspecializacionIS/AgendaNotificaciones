package com.clinic.agenda.application.usecase;

import com.clinic.agenda.api.dto.AgendaCreateRequest;
import com.clinic.agenda.application.port.in.ScheduleAppointmentUseCase;
import com.clinic.agenda.application.port.out.agenda.*;
import com.clinic.agenda.application.port.out.medico.LoadMedicoPort;
import com.clinic.agenda.application.port.out.paciente.LoadPacientePort;
import com.clinic.agenda.application.port.out.notification.SendEmailPort;
import com.clinic.agenda.domain.model.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ScheduleAppointmentService implements ScheduleAppointmentUseCase {

    private final LoadMedicoPort loadMedico;
    private final LoadPacientePort loadPaciente;
    private final SaveAgendaPort saveAgenda;
    private final CheckOverlapPort checkOverlap;
    private final SendEmailPort sendEmail;

    public ScheduleAppointmentService(LoadMedicoPort loadMedico,
                                      LoadPacientePort loadPaciente,
                                      SaveAgendaPort saveAgenda,
                                      CheckOverlapPort checkOverlap,
                                      SendEmailPort sendEmail) {
        this.loadMedico = loadMedico;
        this.loadPaciente = loadPaciente;
        this.saveAgenda = saveAgenda;
        this.checkOverlap = checkOverlap;
        this.sendEmail = sendEmail;
    }

    @Override @Transactional
    public UUID schedule(AgendaCreateRequest req) {
        var medico = loadMedico.findById(req.getMedicoId())
                .orElseThrow(() -> new IllegalArgumentException("Médico no encontrado"));
        var paciente = loadPaciente.findById(req.getPacienteId())
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado"));

        if (!req.getInicio().isBefore(req.getFin()))
            throw new IllegalArgumentException("inicio < fin requerido");
        if (checkOverlap.existsOverlap(medico.getId(), req.getInicio(), req.getFin()))
            throw new IllegalArgumentException("Traslape de cita con otra existente");

        var a = Agenda.builder()
                .id(UUID.randomUUID())
                .medico(medico)
                .paciente(paciente)
                .inicio(req.getInicio())
                .fin(req.getFin())
                .motivo(req.getMotivo())
                .notas(req.getNotas())
                .estado(AgendaEstado.PROGRAMADA)
                .build();

        saveAgenda.save(a);

        try {
            sendEmail.sendAppointmentEmail(
                    paciente.getEmail(),
                    medico.getNombreCompleto(),
                    paciente.getNombreCompleto(),
                    req.getInicio().toString(),
                    req.getFin().toString(),
                    req.getMotivo()
            );
        } catch (Exception ignored) {}

        return a.getId();
    }
}