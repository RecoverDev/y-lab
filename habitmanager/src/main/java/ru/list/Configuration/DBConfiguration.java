package ru.list.Configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import liquibase.integration.spring.SpringLiquibase;
import ru.list.Parameters.Parameters;
import ru.list.Parameters.ReadParameters;

@Configuration
public class DBConfiguration {

    @Bean
    @Value("application.yml")
    public Parameters parameters(String nameFile) {
        ReadParameters readParameters = new ReadParameters(nameFile);
        Parameters parameters = readParameters.read();
        return parameters;
    }
    
    @SuppressWarnings("rawtypes")
    @Bean
    @Value("#{parameters}")
    public DataSource dataSource(Parameters parameters) {

        String url = parameters.getDb().getUrl();
        String user = parameters.getDb().getUser();
        String password = parameters.getDb().getPassword();
        String driver = parameters.getDb().getDriver();
        DataSourceBuilder dataSource = DataSourceBuilder.create();
        dataSource.driverClassName(driver);
        dataSource.username(user);
        dataSource.password(password);
        dataSource.url(url);

        return dataSource.build();
    }

    @Bean
    public SpringLiquibase liquibaseUpdate(DataSource dataSource, Parameters parameters) throws Exception {

        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog(parameters.getLiquibase().getChangelog());
        return liquibase;
    }
}
