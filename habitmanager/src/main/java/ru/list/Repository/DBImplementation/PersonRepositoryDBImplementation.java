package ru.list.Repository.DBImplementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.list.Model.Person;
import ru.list.Repository.PersonRepository;


@Component
public class PersonRepositoryDBImplementation implements PersonRepository {
    private static final String nameTable = "habit.person";
    private static final String nameSerialID = "habit.person_id_seq";
    private DataSource dbConnection = null;

    @Autowired
    Logger log;

    public PersonRepositoryDBImplementation(DataSource dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public boolean save(Person person) {
        boolean result = false;
        PreparedStatement statement = null;
        String sql = String.format("INSERT INTO %s (id, username, email, password, role, blocked) VALUES (nextval('%s'), ?, ?, ?, ?, ?)", nameTable,nameSerialID);
        try (Connection connection = dbConnection.getConnection()) {
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
            log.error(" Ошибка выполенения запроса: " + e.getMessage(), result);
            result = false;
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                log.error(" Ошибка закрытия PreparedStatement: " + e.getMessage(), result);
            }
        }

        return result;
    }

    @Override
    public boolean delete(int id) {
        boolean result = false;
        PreparedStatement statement = null;
        String sql = "DELETE FROM " + nameTable + " WHERE id = ?";
        try (Connection connection = dbConnection.getConnection()) {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            int count = statement.executeUpdate();
            result = (count == 1);
            if (result) {
                connection.commit();
            }
        } catch(SQLException e) {
            log.error(" Ошибка выполенения запроса: " + e.getMessage(), result);
            result = false;
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                log.error(" Ошибка закрытия PreparedStatement: " + e.getMessage(), result);
            }
        }

        return result;
    }

    @Override
    public Person findByEmailAndPassword(String email, String password) {
        Person result = null;
        PreparedStatement statement = null;
        String sql = String.format("SELECT * FROM %s WHERE email = ? AND password = ?",nameTable);
        try (Connection connection = dbConnection.getConnection()) {
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
            log.error("Ошибка получения пользователя: " + e.getMessage(),false);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                log.error(" Ошибка закрытия PreparedStatement: " + e.getMessage(), false);
            }
        }

        return result;
    }

    @Override
    public Person findByPassword(String password) {
        Person result = null;
        PreparedStatement statement = null;
        String sql = String.format("SELECT * FROM %s WHERE password = ?",nameTable);
        try (Connection connection = dbConnection.getConnection()) {
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
            log.error("Ошибка получения пользователя: " + e.getMessage(),false);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                log.error(" Ошибка закрытия PreparedStatement: " + e.getMessage(), false);
            }
        }

        return result;
    }

    @Override
    public List<Person> findAll() {
        List<Person> result = new ArrayList<>();
        PreparedStatement statement = null;
        String sql = "SELECT * FROM " + nameTable;
        try (Connection connection = dbConnection.getConnection()) {
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
            log.error("Ошибка получения списка пользователей: " + e.getMessage(),false);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                log.error(" Ошибка закрытия PreparedStatement: " + e.getMessage(), false);
            }
        }

        return result;
    }

    @Override
    public boolean exist(Person person) {
        boolean result = false;
        PreparedStatement statement = null;
        String sql = String.format("SELECT COUNT(*) FROM %s WHERE id = ?",nameTable);
        try (Connection connection = dbConnection.getConnection()) {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, person.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result = resultSet.getInt("COUNT") > 0;
            }
            resultSet.close();
        } catch(SQLException e) {
            log.error("Ошибка поиска пользователя: " + e.getMessage(),false);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                log.error(" Ошибка закрытия PreparedStatement: " + e.getMessage(), false);
            }
        }

        return result;
    }

}
