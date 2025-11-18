package com.clinic.agenda.application.port.out.notification;

public interface SendEmailPort {
    void sendAppointmentEmail(
            String to,
            String medicoNombre,
            String pacienteNombre,
            String inicioISO,
            String finISO,
            String motivo
    );
}