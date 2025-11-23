package com.clinic.agenda.adapter.in.web;

import com.clinic.agenda.api.dto.AgendaCreateRequest;
import com.clinic.agenda.api.dto.AgendaResponse;
import com.clinic.agenda.api.dto.ChangeAgendaStatusRequest;
import com.clinic.agenda.application.port.in.ChangeAgendaStatusUseCase;
import com.clinic.agenda.application.port.in.ListAgendaQuery;
import com.clinic.agenda.application.port.in.ListAgendaUseCase;
import com.clinic.agenda.application.port.in.ScheduleAppointmentUseCase;
import com.clinic.agenda.domain.model.Agenda;
import com.clinic.agenda.domain.model.AgendaEstado;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/agenda")
public class AgendaController {

    private final ScheduleAppointmentUseCase scheduleUC;
    private final ListAgendaUseCase listAgendaUC;
    private final ChangeAgendaStatusUseCase changeAgendaStatusUC;

    public AgendaController(ScheduleAppointmentUseCase scheduleUC,
                            ListAgendaUseCase listAgendaUC,
                            ChangeAgendaStatusUseCase changeAgendaStatusUC) {
        this.scheduleUC = scheduleUC;
        this.listAgendaUC = listAgendaUC;
        this.changeAgendaStatusUC = changeAgendaStatusUC;
    }

    // ------------------ Crear cita ------------------
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UUID schedule(@Valid @RequestBody AgendaCreateRequest req) {
        return scheduleUC.schedule(req);
    }

    // ------------------ Listar citas con filtros ------------------
    @GetMapping
    public List<AgendaResponse> listar(
            @RequestParam(required = false) UUID medicoId,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam(required = false) AgendaEstado estado
    ) {
        var query = new ListAgendaQuery(medicoId, fecha, estado);

        return listAgendaUC.listar(query)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // ------------------ Cambiar estado de una cita ------------------
    @PatchMapping("/{id}/estado")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cambiarEstado(
            @PathVariable UUID id,
            @Valid @RequestBody ChangeAgendaStatusRequest request
    ) {
        AgendaEstado nuevoEstado = AgendaEstado.valueOf(request.nuevoEstado().toUpperCase());
        changeAgendaStatusUC.cambiarEstado(id, nuevoEstado);
    }

    // ------------------ Mapper interno Agenda -> DTO ------------------
    private AgendaResponse toResponse(Agenda agenda) {
        return new AgendaResponse(
                agenda.getId(),
                agenda.getMedico().getId(),
                agenda.getPaciente().getId(),
                agenda.getInicio(),
                agenda.getFin(),
                agenda.getEstado(),
                agenda.getMotivo(),
                agenda.getNotas()
        );
    }
}