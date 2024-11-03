package ru.list.Configuration;

import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import liquibase.integration.spring.SpringLiquibase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import ru.list.Db.DBConnection;
import ru.list.Repository.HabitRepository;
import ru.list.Repository.LogBookRepository;
import ru.list.Repository.PersonRepository;
import ru.list.Repository.DBImplementation.HabitRepositoryDBImplementation;
import ru.list.Repository.DBImplementation.LogBookRepositoryDBImplementation;
import ru.list.Repository.DBImplementation.PersonRepositoryDBImplementation;
import ru.list.logger.Logger;

@Configuration
@PropertySource("application.yml")
public class DBConfiguration {

    @Autowired
    public Environment environment;

    @Bean
    public DBConnection dbConnection(@Value("#{logger}")Logger logger) {
        DBConnection connection =  new DBConnection(logger);
        connection.setUrl(environment.getProperty("db.url",String.class));
        connection.setUserName(environment.getProperty("db.user",String.class));
        connection.setPassword(environment.getProperty("db.password",String.class));
        return connection;

    }

    @SuppressWarnings("null")
    @Bean
    public SpringLiquibase liquibaseUpdate() {
        SpringLiquibase liquibase = new SpringLiquibase();
        String url = environment.getProperty("db.url",String.class);
        String user = environment.getProperty("db.user",String.class);
        String password = environment.getProperty("db.password",String.class);
        DriverManagerDataSource dataSource = new DriverManagerDataSource(url, user, password);
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog(environment.getProperty("liquibase.changelog",String.class));
        return liquibase;
    }


    @Bean
    public PersonRepository personRepository(@Value("#{dbConnection}") DBConnection dbConnection, @Value("#{logger}")Logger logger) {
        return new PersonRepositoryDBImplementation(dbConnection, logger);
    }

    @Bean
    public HabitRepository habitRepository(@Value("#{dbConnection}") DBConnection dbConnection, @Value("#{logger}")Logger logger) {
        return new HabitRepositoryDBImplementation(dbConnection, logger);
    }

    @Bean
    public LogBookRepository logBookRepository(@Value("#{dbConnection}") DBConnection dbConnection, @Value("#{logger}")Logger logger) {
        return new LogBookRepositoryDBImplementation(dbConnection, logger);
    }

}
