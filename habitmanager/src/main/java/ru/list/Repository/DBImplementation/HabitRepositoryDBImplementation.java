package ru.list.Repository.DBImplementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.list.Db.DBConnection;
import ru.list.Model.Habit;
import ru.list.Model.Period;
import ru.list.Model.Person;
import ru.list.Repository.HabitRepository;
import ru.list.logger.Logger;

public class HabitRepositoryDBImplementation implements HabitRepository {
    private static final String nameTable = "habit.habit";
    private static final String personTable = "habit.person";
    private static final String nameSerialID = "habit.habit_id_seq";
    private DBConnection dbConnection = null;
    private Logger logger = null;

    public HabitRepositoryDBImplementation(DBConnection dbConnection, Logger logger) {
        this.dbConnection = dbConnection;
        this.logger = logger;
    }

    @Override
    public boolean save(Habit habit) {
        boolean result = false;;
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = String.format("INSERT INTO %s (id, name_habit, description, person_id, period_id, registration) VALUES (nextval('%s'), ?, ?, ?, ?, ?)", nameTable,nameSerialID);
        if (!dbConnection.connect()) {
            logger.addRecord("Ошибка подключения к базе при добавлении привычки", result);
            return result;
        }
        try {
            connection = dbConnection.getConnection();
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
    public boolean delete(Habit habit) {
        boolean result = false;;
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "DELETE FROM " + nameTable + " WHERE id = ?";
        if (!dbConnection.connect()) {
            logger.addRecord("Ошибка подключения к базе при удалении привычки", result);
            return result;
        }
        try {
            connection = dbConnection.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(sql);
            statement.setInt(1, habit.getId());
            int count = statement.executeUpdate();
            result = (count == 1);
            if (result) {
                connection.commit();
            }
        } catch(SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.addRecord("Ошибка rollback при удалении Habit: " + ex.getMessage(), result);
            }
            logger.addRecord(" Ошибка соединения с БД: " + e.getMessage(), result);
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
    public List<Habit> findByPerson(Person person) {
        List<Habit> habits = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "SELECT * FROM " + nameTable + " WHERE person_id = ?";
        if (!dbConnection.connect()) {
            logger.addRecord("Ошибка подключения к базе при получении списка привычек", false);
            return habits;
        }
        try {
            connection = dbConnection.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, person.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Habit habit = new Habit(resultSet.getInt("id"), 
                                        resultSet.getString("name_habit"),
                                        resultSet.getString("description"),
                                        person, 
                                        Period.values()[resultSet.getInt("period_id")],
                                        resultSet.getDate("registration").toLocalDate());
                habits.add(habit);
            }
            resultSet.close();
        } catch (SQLException e) {
            logger.addRecord("Ошибка получения списка привычек: " + e.getMessage(),false);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.addRecord(" Ошибка закрытия PreparedStatement: " + e.getMessage(), false);
            }
        }

        dbConnection.closeConnection();
        return habits;  
    }

    @Override
    public List<Habit> findAll() {
        List<Habit> habits = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        if (!dbConnection.connect()) {
            logger.addRecord("Ошибка подключения к базе при получении полного списка привычек", false);
            return habits;
        }

        String sql = String.format("SELECT * FROM %s h JOIN %s p ON h.person_id = p.id",nameTable,personTable);
        try {
            connection = dbConnection.getConnection();
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Person person = new Person(resultSet.getInt("person_id"),
                                resultSet.getString("username"),
                                resultSet.getString("email"),
                                resultSet.getString("password"),
                                resultSet.getInt("role"),
                                resultSet.getBoolean("blocked"));
                Habit habit = new Habit(resultSet.getInt("id"), 
                                resultSet.getString("name_habit"),
                                resultSet.getString("description"),
                                person, 
                                Period.values()[resultSet.getInt("period_id")],
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
                logger.addRecord(" Ошибка закрытия PreparedStatement: " + e.getMessage(), false);
            }
        }

        dbConnection.closeConnection();
        return habits;
    }

    @Override
    public boolean exist(Habit habit) {
        boolean result = false;
        Connection connection = null;
        PreparedStatement statement = null;
        if (!dbConnection.connect()) {
            logger.addRecord("Ошибка подключения к базе при определении наличия привычки", false);
            return false;
        }
        String sql = String.format("SELECT COUNT(*) FROM %s WHERE id = ?",nameTable);
        try {
            connection = dbConnection.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, habit.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result = resultSet.getInt("COUNT") > 0;
            }
            resultSet.close();
        } catch(SQLException e) {
            logger.addRecord("Ошибка подключения к БД", result);
            result = false;
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
