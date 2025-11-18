package com.clinic.agenda.adapter.in.web;

import com.clinic.agenda.api.dto.MedicoDto;

import com.clinic.agenda.application.usecase.CreateMedicoService;
import com.clinic.agenda.application.usecase.UpdateMedicoService;
import com.clinic.agenda.application.usecase.ListMedicosService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/medicos")
public class MedicoController {

    private final CreateMedicoService createService;
    private final UpdateMedicoService updateService;
    private final ListMedicosService listService;

    public MedicoController(CreateMedicoService createService,
                            UpdateMedicoService updateService,
                            ListMedicosService listService) {
        this.createService = createService;
        this.updateService = updateService;
        this.listService = listService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UUID crear(@Valid @RequestBody MedicoDto dto) {
        return createService.crear(dto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void actualizar(@PathVariable UUID id,
                           @Valid @RequestBody MedicoDto dto) {
        updateService.actualizar(id, dto);
    }

    @GetMapping
    public List<MedicoDto> listar() {
        return listService.listar();
    }
}