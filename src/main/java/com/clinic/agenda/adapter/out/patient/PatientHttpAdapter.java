package com.clinic.agenda.adapter.out.patient;

import com.clinic.agenda.application.port.out.paciente.LoadPacientePort;
import com.clinic.agenda.domain.model.Paciente;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Primary
public class PatientHttpAdapter implements LoadPacientePort {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    // baseUrl viene de application.properties: patient.service.base-url
    public PatientHttpAdapter(RestTemplate restTemplate,
                              @Value("${patient.service.base-url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    @Override
    public Optional<Paciente> findById(UUID id) {
        String url = baseUrl + "/api/patients/" + id;

        try {
            PatientClientDto dto = restTemplate.getForObject(url, PatientClientDto.class);
            if (dto == null) {
                return Optional.empty();
            }
            return Optional.of(mapToDomain(dto));
        } catch (Exception ex) {
            // aquí podrías loguear el error si quieres
            return Optional.empty();
        }
    }

    @Override
    public Optional<Paciente> findByNumeroIdentificacion(String numeroIdentificacion) {
        // Lo puedes implementar más adelante si tu microservicio de pacientes expone un endpoint por documento
        return Optional.empty();
    }

    @Override
    public List<Paciente> findAll() {
        // Igual, si luego tienes un GET /api/patients que quieras usar
        return Collections.emptyList();
    }

    // ================= MAPPER PatientClientDto -> Paciente (dominio) =================

    private Paciente mapToDomain(PatientClientDto dto) {
        return Paciente.builder()
                .id(UUID.fromString(dto.getId()))
                // Como tu Paciente solo tiene nombreCompleto, armamos el nombre:
                .nombreCompleto(dto.getFirstName() + " " + dto.getLastName())
                .email(dto.getEmail())
                .fechaNacimiento(dto.getBirthDate())
                // numeroIdentificacion, genero, direccion, telefono, activo
                // se dejan null / por defecto en este microservicio, porque el maestro es el MS de pacientes
                .build();
    }
}