package ru.list;

import ru.list.Db.DBConnection;
import ru.list.Db.Migration;

public class Main {
    public static void main(String[] args) {
        HabitProperties properties = new HabitProperties("application.properties");
        properties.load();

        DBConnection connection = new DBConnection(properties);
        connection.connect();
        
        Migration migration = new Migration(connection.getConnection());
        boolean resultMigration = migration.migrate();

        if (resultMigration) {
            MainCycle mainCycle = new MainCycle(connection);
            mainCycle.run();
        }
    }
}