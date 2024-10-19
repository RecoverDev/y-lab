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


    public boolean migrate(String changelogFile) {
        boolean result = false;
        if (changelogFile.length() == 0) {
            changelogFile = "db/changelog/changelog.xml";
        }
        try {
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new Liquibase(changelogFile, new ClassLoaderResourceAccessor(), database);
            liquibase.update();
            liquibase.close();
            result = true;
        } catch(LiquibaseException e) {
            System.out.println("Ошибка миграции: " + e.getMessage());
        }
        return result;
    }

}
