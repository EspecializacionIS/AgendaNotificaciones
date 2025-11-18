package com.clinic.agenda.adapter.in.web;

import com.clinic.agenda.api.dto.PacienteDto;
import com.clinic.agenda.application.port.in.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/pacientes")
public class PacienteController {

    private final CreatePacienteUseCase createUC;
    private final UpdatePacienteUseCase updateUC;
    private final ListPacientesUseCase listUC;

    public PacienteController(CreatePacienteUseCase createUC, UpdatePacienteUseCase updateUC, ListPacientesUseCase listUC) {
        this.createUC = createUC; this.updateUC = updateUC; this.listUC = listUC;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UUID crear(@Valid @RequestBody PacienteDto dto) {
        return createUC.crear(dto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void actualizar(@PathVariable UUID id, @Valid @RequestBody PacienteDto dto) {
        updateUC.actualizar(id, dto);
    }

    @GetMapping
    public List<PacienteDto> listar() {
        return listUC.listar();
    }
}
