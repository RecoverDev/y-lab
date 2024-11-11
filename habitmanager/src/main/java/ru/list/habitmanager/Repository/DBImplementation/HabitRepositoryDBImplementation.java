package ru.list.habitmanager.Repository.DBImplementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import ru.list.habitmanager.Model.Habit;
import ru.list.habitmanager.Model.Period;
import ru.list.habitmanager.Model.Person;
import ru.list.habitmanager.Model.Role;
import ru.list.habitmanager.Repository.HabitRepository;



@Repository
public class HabitRepositoryDBImplementation implements HabitRepository {
    private static final String nameTable = "habit.habit";
    private static final String personTable = "habit.person";
    private static final String nameSerialID = "habit.habit_id_seq";
    private final DataSource dbConnection;

    private Logger log = LoggerFactory.getLogger(HabitRepositoryDBImplementation.class);

    public HabitRepositoryDBImplementation(DataSource dbConnection) {
        this.dbConnection = dbConnection;
    }

    @SuppressWarnings("null")
    @Override
    public boolean save(Habit habit) {
        boolean result = false;
        PreparedStatement statement = null;
        String sql = String.format("INSERT INTO %s (id, name_habit, description, person_id, period, registration) VALUES (nextval('%s'), ?, ?, ?, ?, ?)", nameTable,nameSerialID);
        try (Connection connection = dbConnection.getConnection()) {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(sql);
            statement.setString(1, habit.getName());
            statement.setString(2, habit.getDescription());
            statement.setInt(3, habit.getPerson().getId());
            statement.setInt(4, habit.getPeriod().ordinal());
            statement.setDate(5, java.sql.Date.valueOf(habit.getRegistration()));
            int count = statement.executeUpdate();
            result = (count == 1);
            if (result) {
                connection.commit();
            }
        } catch(SQLException e) {
            log.error(" Ошибка выполенения запроса: ", e);
            result = false;
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                log.error(" Ошибка закрытия PreparedStatement: ",e.getMessage());
            }
        }
        return result;
    }

    @SuppressWarnings("null")
    @Override
    public boolean delete(Habit habit) {
        boolean result = false;
        PreparedStatement statement = null;
        String sql = "DELETE FROM " + nameTable + " WHERE id = ?";
        try (Connection connection = dbConnection.getConnection()) {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(sql);
            statement.setInt(1, habit.getId());
            int count = statement.executeUpdate();
            result = (count == 1);
            if (result) {
                connection.commit();
            }
        } catch(SQLException e) {
            log.error(" Ошибка соединения с БД: " + e.getMessage(), result);
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

    @SuppressWarnings("null")
    @Override
    public List<Habit> findByPerson(Person person) {
        List<Habit> habits = new ArrayList<>();
        PreparedStatement statement = null;
        String sql = "SELECT * FROM " + nameTable + " WHERE person_id = ?";
        try (Connection connection = dbConnection.getConnection()) {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, person.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Habit habit = new Habit(resultSet.getInt("id"), 
                                        resultSet.getString("name_habit"),
                                        resultSet.getString("description"),
                                        person, 
                                        Period.values()[resultSet.getInt("period")],
                                        resultSet.getDate("registration").toLocalDate());
                habits.add(habit);
            }
            resultSet.close();
        } catch (SQLException e) {
            log.error("Ошибка получения списка привычек: " + e.getMessage(),false);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                log.error(" Ошибка закрытия PreparedStatement: " + e.getMessage(), false);
            }
        }

        return habits;  
    }

    @SuppressWarnings("null")
    @Override
    public List<Habit> findAll() {
        List<Habit> habits = new ArrayList<>();
        PreparedStatement statement = null;

        String sql = String.format("SELECT * FROM %s h JOIN %s p ON h.person_id = p.id",nameTable,personTable);
        try (Connection connection = dbConnection.getConnection()) {
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Person person = new Person(resultSet.getInt("person_id"),
                                resultSet.getString("username"),
                                resultSet.getString("email"),
                                resultSet.getString("password"),
                                Role.valueOf(resultSet.getString("role")),
                                resultSet.getBoolean("blocked"));
                Habit habit = new Habit(resultSet.getInt("id"), 
                                resultSet.getString("name_habit"),
                                resultSet.getString("description"),
                                person, 
                                Period.values()[resultSet.getInt("period")],
                                resultSet.getDate("registration").toLocalDate());
                habits.add(habit);
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к БД: " + e.getMessage());
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                log.error(" Ошибка закрытия PreparedStatement: " + e.getMessage(), false);
            }
        }

        return habits;
    }

    @SuppressWarnings("null")
    @Override
    public boolean exist(Habit habit) {
        boolean result = false;
        PreparedStatement statement = null;
        String sql = String.format("SELECT COUNT(*) FROM %s WHERE id = ?",nameTable);
        try (Connection connection = dbConnection.getConnection()) {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, habit.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result = resultSet.getInt("COUNT") > 0;
            }
            resultSet.close();
        } catch(SQLException e) {
            log.error("Ошибка подключения к БД", result);
            result = false;
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                log.error(" Ошибка закрытия PreparedStatement: " + e.getMessage(), false);
            }
        }

        return result;
    }

    @SuppressWarnings("null")
    @Override
    public Habit findById(int id) {
        Habit habit = null;
        PreparedStatement statement = null;
        String sql = String.format("SELECT * FROM %s h JOIN %s p ON h.person_id = p.id WHERE h.id = ?",nameTable,personTable);
        try (Connection connection = dbConnection.getConnection()) {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Person person = new Person(resultSet.getInt("person_id"),
                                resultSet.getString("username"),
                                resultSet.getString("email"),
                                resultSet.getString("password"),
                                Role.valueOf(resultSet.getString("role")),
                                resultSet.getBoolean("blocked"));
                habit = new Habit(resultSet.getInt("id"), 
                                resultSet.getString("name_habit"),
                                resultSet.getString("description"),
                                person, 
                                Period.values()[resultSet.getInt("period")],
                                resultSet.getDate("registration").toLocalDate());
            }
            resultSet.close();
        } catch (SQLException e) {
            log.error("Ошибка получения списка привычек: " + e.getMessage(),false);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                log.error(" Ошибка закрытия PreparedStatement: " + e.getMessage(), false);
            }
        }

        return habit;  
    }

}
