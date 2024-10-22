package ru.list.Repository.DBImplementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.list.Db.DBConnection;
import ru.list.Model.Person;
import ru.list.Repository.PersonRepository;
import ru.list.logger.Logger;

public class PersonRepositoryDBImplementation implements PersonRepository {
    private static final String nameTable = "habit.person";
    private static final String nameSerialID = "habit.person_id_seq";
    private DBConnection dbConnection = null;
    private Logger logger = null;

    public PersonRepositoryDBImplementation(DBConnection dbConnection, Logger logger) {
        this.dbConnection = dbConnection;
        this.logger = logger;
    }

    @Override
    public boolean save(Person person) {
        boolean result = false;
        Connection connection = null;
        PreparedStatement statement = null;
        if (!dbConnection.connect()) {
            logger.addRecord("Ошибка подключения к базе при добавлении пользователя", result);
            return result;
        }
        String sql = String.format("INSERT INTO %s (id, username, email, password, role, blocked) VALUES (nextval('%s'), ?, ?, ?, ?, ?)", nameTable,nameSerialID);
        try {
            connection = dbConnection.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(sql);
            statement.setString(1, person.getName());
            statement.setString(2, person.getEmail());
            statement.setString(3, person.getPassword());
            statement.setInt(4, person.getRole());
            statement.setBoolean(5, person.isBlocked());
            int count = statement.executeUpdate();
            result = (count == 1);
            if (result) {
                connection.commit();
            }
        } catch(SQLException e) {
            try {
                connection.rollback();
            } catch(SQLException ex) {
                logger.addRecord("Ошибка rollback: " + ex.getMessage(), result);
            }
            logger.addRecord(" Ошибка выполенения запроса: " + e.getMessage(), result);
            result = false;
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.addRecord(" Ошибка закрытия PreparedStatement: " + e.getMessage(), result);
            }
        }

        dbConnection.closeConnection();
        return result;
    }

    @Override
    public boolean delete(Person person) {
        boolean result = false;
        Connection connection = null;
        PreparedStatement statement = null;
        if (!dbConnection.connect()) {
            logger.addRecord("Ошибка подключения к базе при удалении пользователя", result);
            return result;
        }
        String sql = "DELETE FROM " + nameTable + " WHERE id = ?";
        try {
            connection = dbConnection.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(sql);
            statement.setInt(1, person.getId());
            int count = statement.executeUpdate();
            result = (count == 1);
            if (result) {
                connection.commit();
            }
        } catch(SQLException e) {
            try {
                connection.rollback();
            } catch(SQLException ex) {
                logger.addRecord("Ошибка rollback: " + ex.getMessage(), result);
            }
            logger.addRecord(" Ошибка выполенения запроса: " + e.getMessage(), result);
            result = false;
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.addRecord(" Ошибка закрытия PreparedStatement: " + e.getMessage(), result);
            }
        }

        dbConnection.closeConnection();
        return result;
    }

    @Override
    public Person findByEmailAndPassword(String email, String password) {
        Person result = null;
        Connection connection = null;
        PreparedStatement statement = null;
        if (!dbConnection.connect()) {
            logger.addRecord("Ошибка подключения к базе при поиске пользователя", false);
            return result;
        }
        String sql = String.format("SELECT * FROM %s WHERE email = ? AND password = ?",nameTable);
        try {
            connection = dbConnection.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result = new Person(resultSet.getInt("id"),
                                resultSet.getString("username"),
                                resultSet.getString("email"),
                                resultSet.getString("password"),
                                resultSet.getInt("role"),
                                resultSet.getBoolean("blocked"));
            }
            resultSet.close();
        } catch (SQLException e) {
            logger.addRecord("Ошибка получения пользователя: " + e.getMessage(),false);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.addRecord(" Ошибка закрытия PreparedStatement: " + e.getMessage(), false);
            }
        }

        dbConnection.closeConnection();
        return result;
    }

    @Override
    public Person findByPassword(String password) {
        Person result = null;
        Connection connection = null;
        PreparedStatement statement = null;
        if (!dbConnection.connect()) {
            logger.addRecord("Ошибка подключения к базе при поиске пользователя", false);
            return result;
        }
        String sql = String.format("SELECT * FROM %s WHERE password = ?",nameTable);
        try {
            connection = dbConnection.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, password);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result = new Person(resultSet.getInt("id"),
                                resultSet.getString("username"),
                                resultSet.getString("email"),
                                resultSet.getString("password"),
                                resultSet.getInt("role"),
                                resultSet.getBoolean("blocked"));
            }
            resultSet.close();
        } catch(SQLException e) {
            logger.addRecord("Ошибка получения пользователя: " + e.getMessage(),false);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.addRecord(" Ошибка закрытия PreparedStatement: " + e.getMessage(), false);
            }
        }

        dbConnection.closeConnection();
        return result;
    }

    @Override
    public List<Person> findAll() {
        List<Person> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        if (!dbConnection.connect()) {
            logger.addRecord("Ошибка подключения к базе при получении списка пользователей", false);
            return result;
        }
        String sql = "SELECT * FROM " + nameTable;
        try {
            connection = dbConnection.getConnection();
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Person person = new Person(resultSet.getInt("id"),
                                resultSet.getString("username"),
                                resultSet.getString("email"),
                                resultSet.getString("password"),
                                resultSet.getInt("role"),
                                resultSet.getBoolean("blocked"));
                result.add(person);
            }
            resultSet.close();
        } catch(SQLException e) {
            logger.addRecord("Ошибка получения списка пользователей: " + e.getMessage(),false);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.addRecord(" Ошибка закрытия PreparedStatement: " + e.getMessage(), false);
            }
        }

        dbConnection.closeConnection();
        return result;
    }

    @Override
    public boolean exist(Person person) {
        boolean result = false;
        Connection connection = null;
        PreparedStatement statement = null;
        if (!dbConnection.connect()) {
            logger.addRecord("Ошибка подключения к базе при поиске пользователя", false);
            return result;
        }
        String sql = String.format("SELECT COUNT(*) FROM %s WHERE id = ?",nameTable);
        try {
            connection = dbConnection.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, person.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result = resultSet.getInt("COUNT") > 0;
            }
            resultSet.close();
        } catch(SQLException e) {
            logger.addRecord("Ошибка поиска пользователя: " + e.getMessage(),false);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.addRecord(" Ошибка закрытия PreparedStatement: " + e.getMessage(), false);
            }
        }

        dbConnection.closeConnection();
        return result;
    }

}
