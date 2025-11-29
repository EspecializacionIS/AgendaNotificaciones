package com.clinic.agenda.adapter.out.user;
import com.clinic.agenda.application.port.out.usuario.UsersGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import com.clinic.agenda.api.dto.UserServiceResponse;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class UsersApiAdapter implements UsersGateway {

    private final WebClient usersWebClient;

    @Override
    public UserServiceResponse getUserByUsername(String username) {
        return usersWebClient.get()
                .uri("/users/{username}", username)
                .retrieve()
                .bodyToMono(UserServiceResponse.class)
                .block();
    }
}
