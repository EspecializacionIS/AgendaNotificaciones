package com.clinic.agenda.application.usecase;

import com.clinic.agenda.application.port.in.ChangeAgendaStatusUseCase;
import com.clinic.agenda.application.port.in.ListAgendaQuery;
import com.clinic.agenda.application.port.in.ListAgendaUseCase;
import com.clinic.agenda.application.port.out.agenda.LoadAgendaPort;
import com.clinic.agenda.application.port.out.agenda.SaveAgendaPort;
import com.clinic.agenda.domain.model.Agenda;
import com.clinic.agenda.domain.model.AgendaEstado;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class AgendaQueryService implements ListAgendaUseCase, ChangeAgendaStatusUseCase {

    private final LoadAgendaPort loadAgendaPort;
    private final SaveAgendaPort saveAgendaPort;

    public AgendaQueryService(LoadAgendaPort loadAgendaPort,
                              SaveAgendaPort saveAgendaPort) {
        this.loadAgendaPort = loadAgendaPort;
        this.saveAgendaPort = saveAgendaPort;
    }

    @Override
    public List<Agenda> listar(ListAgendaQuery query) {
        return loadAgendaPort.findByFiltros(
                query.medicoId(),
                query.fecha(),
                query.estado()
        );
    }

    @Override
    public void cambiarEstado(UUID agendaId, AgendaEstado nuevoEstado) {
        Agenda agenda = loadAgendaPort.findById(agendaId)
                .orElseThrow(() -> new IllegalArgumentException("Cita/Agenda no encontrada: " + agendaId));

        // 👉 Opción A (recomendada): si en Agenda tienes lógica de negocio
        // agenda.cambiarEstado(nuevoEstado);

        // 👉 Opción B (simple): si aún no tienes método de dominio, por ahora:
        agenda.setEstado(nuevoEstado);

        saveAgendaPort.save(agenda);
    }
}