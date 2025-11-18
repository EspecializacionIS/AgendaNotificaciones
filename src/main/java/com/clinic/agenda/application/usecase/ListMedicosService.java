package com.clinic.agenda.application.usecase;

import com.clinic.agenda.api.dto.MedicoDto;
import com.clinic.agenda.application.port.in.ListMedicosUseCase;
import com.clinic.agenda.application.port.out.medico.LoadMedicoPort;
import com.clinic.agenda.domain.model.Medico;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListMedicosService implements ListMedicosUseCase {

    private final LoadMedicoPort load;

    public ListMedicosService(LoadMedicoPort load) {
        this.load = load;
    }

    @Override
    public List<MedicoDto> listar() {
        return load.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    private MedicoDto toDto(Medico m) {
        MedicoDto dto = new MedicoDto();
        dto.setId(m.getId());
        dto.setNombreCompleto(m.getNombreCompleto());
        dto.setEspecialidad(m.getEspecialidad());
        dto.setEmail(m.getEmail());
        dto.setTelefono(m.getTelefono());
        dto.setJornadaInicio(m.getJornadaInicio());
        dto.setJornadaFin(m.getJornadaFin());
        dto.setActivo(m.getActivo());
        return dto;
    }
}