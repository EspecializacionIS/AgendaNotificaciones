package com.clinic.agenda.application.usecase;

import com.clinic.agenda.api.dto.PacienteDto;
import com.clinic.agenda.application.port.in.*;
import com.clinic.agenda.application.port.out.paciente.*;
import com.clinic.agenda.domain.model.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CreatePacienteService implements CreatePacienteUseCase {
    private final SavePacientePort save;
    public CreatePacienteService(SavePacientePort save) { this.save = save; }

    @Override
    public UUID crear(PacienteDto dto) {
        Paciente p = Paciente.builder()
                .id(dto.getId() != null ? dto.getId() : UUID.randomUUID())
                .numeroIdentificacion(dto.getNumeroIdentificacion())
                .nombreCompleto(dto.getNombreCompleto())
                .fechaNacimiento(dto.getFechaNacimiento())
                .genero(Genero.valueOf(dto.getGenero()))
                .direccion(dto.getDireccion())
                .telefono(dto.getTelefono())
                .email(dto.getEmail())
                .activo(dto.getActivo() != null ? dto.getActivo() : true)
                .build();
        return save.save(p).getId();
    }
}

@Service
class UpdatePacienteService implements UpdatePacienteUseCase {
    private final LoadPacientePort load;
    private final SavePacientePort save;
    public UpdatePacienteService(LoadPacientePort load, SavePacientePort save) { this.load = load; this.save = save; }

    @Override
    public void actualizar(UUID id, PacienteDto dto) {
        var p = load.findById(id).orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado"));
        p.setNumeroIdentificacion(dto.getNumeroIdentificacion());
        p.setNombreCompleto(dto.getNombreCompleto());
        p.setFechaNacimiento(dto.getFechaNacimiento());
        p.setGenero(Genero.valueOf(dto.getGenero()));
        p.setDireccion(dto.getDireccion());
        p.setTelefono(dto.getTelefono());
        p.setEmail(dto.getEmail());
        p.setActivo(dto.getActivo() != null ? dto.getActivo() : p.getActivo());
        save.save(p);
    }
}

@Service
class ListPacientesService implements ListPacientesUseCase {
    private final LoadPacientePort load;
    public ListPacientesService(LoadPacientePort load) { this.load = load; }

    @Override
    public List<PacienteDto> listar() {
        return load.findAll().stream().map(p -> PacienteDto.builder()
                .id(p.getId())
                .numeroIdentificacion(p.getNumeroIdentificacion())
                .nombreCompleto(p.getNombreCompleto())
                .fechaNacimiento(p.getFechaNacimiento())
                .genero(p.getGenero().name())
                .direccion(p.getDireccion())
                .telefono(p.getTelefono())
                .email(p.getEmail())
                .activo(p.getActivo())
                .build()).toList();
    }
}