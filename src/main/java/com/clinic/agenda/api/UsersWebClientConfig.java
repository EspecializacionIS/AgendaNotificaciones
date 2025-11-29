package com.clinic.agenda.api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
public class UsersWebClientConfig {

    @Value("${users.service.url}")
    private String usersBaseUrl;

    @Bean
    public WebClient usersWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl(usersBaseUrl)
                .build();
    }
}

