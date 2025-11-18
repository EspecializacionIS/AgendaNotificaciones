package com.clinic.agenda.application.port.out.agenda;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface CheckOverlapPort {
    boolean existsOverlap(UUID medicoId, OffsetDateTime inicio, OffsetDateTime fin);
}