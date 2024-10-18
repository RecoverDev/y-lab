package ru.list.Db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import ru.list.HabitProperties;

/**
 * класс создает соедитение с базой данных
 */
public class DBConnection {
    private String userName;
    private String password;
    private String url;
    private Connection connection = null;

    public DBConnection(HabitProperties properties) {
        this.userName = properties.getUser();
        this.password = properties.getPassword();
        this.url = properties.getUrl();
    }

    /**
     * создание подключения
     * @return - результат подключения (true - успех/false - неуспех)
     */
    public boolean connect() {
        boolean result = false;

        try {
            connection = DriverManager.getConnection(url, userName, password);
            result = true;
        } catch(SQLException e) {
        }

        return result;
    }

    /**
     * возвращает установленное соединение
     * @return - Connection
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * закрывает установленное соединение
     */
    public void closeConnection() {
        try {
            connection.close();
        } catch(SQLException e) {

        }
    }
}
