package com.clinic.agenda.application.usecase;

import com.clinic.agenda.api.dto.MedicoDto;
import com.clinic.agenda.application.port.in.CreateMedicoUseCase;
import com.clinic.agenda.application.port.out.medico.SaveMedicoPort;
import com.clinic.agenda.domain.model.Medico;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateMedicoService implements CreateMedicoUseCase {

    private final SaveMedicoPort save;

    public CreateMedicoService(SaveMedicoPort save) {
        this.save = save;
    }

    @Override
    public UUID crear(MedicoDto dto) {
        Medico m = Medico.builder()
                .id(dto.getId() != null ? dto.getId() : UUID.randomUUID())
                .nombreCompleto(dto.getNombreCompleto())
                .especialidad(dto.getEspecialidad())
                .email(dto.getEmail())
                .telefono(dto.getTelefono())
                .jornadaInicio(dto.getJornadaInicio())
                .jornadaFin(dto.getJornadaFin())
                .activo(dto.getActivo() != null ? dto.getActivo() : true)
                .build();
        return save.save(m).getId();
    }
}