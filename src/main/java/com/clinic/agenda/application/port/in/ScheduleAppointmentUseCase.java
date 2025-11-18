package com.clinic.agenda.application.port.in;

import com.clinic.agenda.api.dto.AgendaCreateRequest;
import java.util.UUID;

public interface ScheduleAppointmentUseCase {
    UUID schedule(AgendaCreateRequest request);
}