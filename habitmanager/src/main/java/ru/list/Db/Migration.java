package ru.list.Db;

import java.sql.Connection;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

public class Migration {
    private Connection connection;


    public Migration(Connection connection) {
        this.connection = connection;
    }


    public boolean migrate() {
        boolean result = false;
        try {
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new Liquibase("db/changelog/changelog.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update();
            liquibase.close();
            result = true;
        } catch(LiquibaseException e) {
            System.out.println("Ошибка миграции: " + e.getMessage());
        }
        return result;
    }

}
