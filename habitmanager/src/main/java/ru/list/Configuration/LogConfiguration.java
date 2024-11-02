package ru.list.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.list.logger.Logger;

@Configuration
public class LogConfiguration {

    @Bean
    public Logger logger() {
        return new Logger();
    }

}
