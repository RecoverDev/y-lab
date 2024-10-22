package ru.list;

import ru.list.Db.DBConnection;
import ru.list.Db.Migration;
import ru.list.logger.Logger;

public class Main {
    public static void main(String[] args) {
        Logger logger = new Logger();
        logger.addRecord("Начало работы программы", true);

        HabitProperties properties = new HabitProperties("application.properties");
        properties.load();

        DBConnection connection = new DBConnection(properties, logger);
        connection.connect();
        
        Migration migration = new Migration(connection.getConnection());
        boolean resultMigration = migration.migrate(properties.getChangelogFile());

        if (resultMigration) {
            logger.addRecord("Миграция прошла успешно", resultMigration);
            MainCycle mainCycle = new MainCycle(connection, logger);
            mainCycle.run();
        } else {
            logger.addRecord("Ошибка миграции", resultMigration);
        }
        logger.addRecord("Работа программы завершена", true);
    }
}