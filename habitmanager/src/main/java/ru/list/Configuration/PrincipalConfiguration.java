package ru.list.Configuration;

import org.springframework.context.annotation.Bean;

import ru.list.Principal;

public class PrincipalConfiguration {

    @Bean
    public Principal principal() {
        return new Principal();
    }

}
