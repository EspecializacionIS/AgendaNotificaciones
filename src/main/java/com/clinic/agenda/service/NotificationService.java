package com.clinic.agenda.service;

import com.clinic.agenda.domain.model.Agenda;
import com.clinic.agenda.domain.model.Paciente;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
public class NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Value("${app.notifications.enabled:true}")
    private boolean notificationsEnabled;

    @Value("${app.notifications.from:no-reply@localhost}")
    private String from;

    @Value("${app.notifications.replyTo:}")
    private String replyTo;

    public NotificationService(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Async
    public void enviarCorreoCitaCreada(Paciente paciente, Agenda agenda, String medicoNombre) {
        if (!notificationsEnabled) { 
            log.info("[EMAIL OFF] Simulando envío correo a {} por cita {}", paciente.getEmail(), agenda.getId());
            return; 
        }
        if (paciente.getEmail() == null || paciente.getEmail().isBlank()) {
            log.warn("Paciente sin email. No se envía notificación. Paciente: {}", paciente.getId());
            return;
        }

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(from);
            if (replyTo != null && !replyTo.isBlank()) helper.setReplyTo(replyTo);
            helper.setTo(paciente.getEmail());
            helper.setSubject("Confirmación de cita — Clínica XYZ");

            // Construir HTML con Thymeleaf
            Context ctx = new Context(new Locale("es", "CO"));
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm (zzz)");
            ctx.setVariable("pacienteNombre", paciente.getNombreCompleto());
            ctx.setVariable("medicoNombre", medicoNombre);
            ctx.setVariable("inicio", agenda.getInicio().format(fmt));
            ctx.setVariable("fin", agenda.getFin().format(fmt));
            ctx.setVariable("motivo", agenda.getMotivo() == null ? "Consulta" : agenda.getMotivo());

            String html = templateEngine.process("agenda-creada", ctx);
            helper.setText(html, true);

            mailSender.send(message);
            log.info("Correo de cita enviada a {}", paciente.getEmail());
        } catch (MessagingException e) {
            log.error("Error enviando correo de cita {} a {}: {}", agenda.getId(), paciente.getEmail(), e.getMessage());
        } catch (Exception e) {
            log.error("Fallo inesperado enviando correo: {}", e.getMessage(), e);
        }
    }
}