package com.clinic.agenda.adapter.in.web;

import com.clinic.agenda.api.dto.AgendaCreateRequest;
import com.clinic.agenda.application.port.in.ScheduleAppointmentUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/agenda")
public class AgendaController {

    private final ScheduleAppointmentUseCase scheduleUC;

    public AgendaController(ScheduleAppointmentUseCase scheduleUC) {
        this.scheduleUC = scheduleUC;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UUID schedule(@Valid @RequestBody AgendaCreateRequest req) {
        return scheduleUC.schedule(req);
    }
}