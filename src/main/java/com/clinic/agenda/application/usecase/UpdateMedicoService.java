package com.clinic.agenda.application.usecase;

import com.clinic.agenda.api.dto.MedicoDto;
import com.clinic.agenda.application.port.in.UpdateMedicoUseCase;
import com.clinic.agenda.application.port.out.medico.LoadMedicoPort;
import com.clinic.agenda.application.port.out.medico.SaveMedicoPort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateMedicoService implements UpdateMedicoUseCase {

    private final LoadMedicoPort load;
    private final SaveMedicoPort save;

    public UpdateMedicoService(LoadMedicoPort load, SaveMedicoPort save) {
        this.load = load;
        this.save = save;
    }

    @Override
    public void actualizar(UUID id, MedicoDto dto) {
        var m = load.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Médico no encontrado"));

        m.setNombreCompleto(dto.getNombreCompleto());
        m.setEspecialidad(dto.getEspecialidad());
        m.setEmail(dto.getEmail());
        m.setTelefono(dto.getTelefono());
        m.setJornadaInicio(dto.getJornadaInicio());
        m.setJornadaFin(dto.getJornadaFin());
        m.setActivo(dto.getActivo() != null ? dto.getActivo() : m.getActivo());

        save.save(m);
    }
}
