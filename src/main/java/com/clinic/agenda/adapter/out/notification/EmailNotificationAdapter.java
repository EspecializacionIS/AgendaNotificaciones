package com.clinic.agenda.adapter.out.notification;


import com.clinic.agenda.application.port.out.notification.SendEmailPort;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class EmailNotificationAdapter implements SendEmailPort {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Value("${app.mail.from:noreply@clinicavet.com}")
    private String from;

    @Value("${app.mail.from-name:Clínica Veterinaria}")
    private String fromName;

    public EmailNotificationAdapter(JavaMailSender mailSender,
                                    TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public void sendAppointmentEmail(String to,
                                     String medicoNombre,
                                     String pacienteNombre,
                                     String inicioISO,
                                     String finISO,
                                     String motivo) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setFrom(from, fromName);
            helper.setSubject("Cita agendada - " + pacienteNombre);

            // Variables para la plantilla Thymeleaf
            Context ctx = new Context();
            ctx.setVariable("paciente", pacienteNombre);
            ctx.setVariable("medico", medicoNombre);
            ctx.setVariable("inicio", inicioISO);
            ctx.setVariable("fin", finISO);
            ctx.setVariable("motivo", motivo);

            String html = templateEngine.process("agenda-creada", ctx);
            helper.setText(html, true);

            mailSender.send(message);

        } catch (MessagingException e) {
            // Aquí puedes loguear el error; en tu use case ya envuelves en try/catch
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
