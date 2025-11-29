package com.clinic.agenda.application.port.out.usuario;
import com.clinic.agenda.api.dto.UserServiceResponse;

public interface UsersGateway {
    UserServiceResponse getUserByUsername(String username);
}

