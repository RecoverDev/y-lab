package ru.list.Repository.DBImplementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.list.Db.DBConnection;
import ru.list.Model.Habit;
import ru.list.Model.LogBook;
import ru.list.Model.Period;
import ru.list.Model.Person;
import ru.list.Repository.LogBookRepository;
import ru.list.logger.Logger;

public class LogBookRepositoryDBImplementation implements LogBookRepository {
    private static final String nameTable = "habit.logbook";
    private static final String habitTable = "habit.habit";
    private static final String personTable = "habit.person";
    private static final String nameSerialID = "habit.logbook_id_seq";
    private DBConnection dbConnection = null;
    private Logger logger = null;

    public LogBookRepositoryDBImplementation(DBConnection dbConnection, Logger logger) {
        this.dbConnection = dbConnection;
        this.logger = logger;
    }


    @Override
    public boolean save(LogBook logBook) {
        boolean result = false;
        Connection connection = null;
        PreparedStatement statement = null;
        if (!dbConnection.connect()) {
            logger.addRecord("Ошибка подключения к базе при добавлении записи в журнал", result);
            return result;
        }
        String sql = String.format("INSERT INTO %s (id, habit_id, date) VALUES (nextval('%s'), ?, ?)", nameTable,nameSerialID);
        try {
            connection = dbConnection.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(sql);
            statement.setInt(1, logBook.getHabit().getId());
            statement.setDate(2, java.sql.Date.valueOf(logBook.getDate()));
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
    public boolean delete(LogBook logBook) {
        boolean result = false;
        Connection connection = null;
        PreparedStatement statement = null;
        if (!dbConnection.connect()) {
            logger.addRecord("Ошибка подключения к базе при удалении записи в журнал", result);
            return result;
        }
        String sql = "DELETE FROM " + nameTable + " WHERE id = ?";
        try {
            connection = dbConnection.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(sql);
            statement.setInt(1, logBook.getId());
            int count = statement.executeUpdate();
            result = (count == 1);
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
    public List<LogBook> findByPerson(Person person) {
        List<LogBook> logBooks = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        if (!dbConnection.connect()) {
            logger.addRecord("Ошибка подключения к базе при поиске записей в журнале", false);
            return logBooks;
        }
        String sql = String.format("SELECT * FROM %s l JOIN %s h ON l.habit_id = h.id WHERE h.person_id = ? ",nameTable,habitTable);
        try {
            connection = dbConnection.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, person.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Habit habit = new Habit(resultSet.getInt("habit_id"), 
                                resultSet.getString("name_habit"),
                                resultSet.getString("description"),
                                person, 
                                Period.values()[resultSet.getInt("period_id")],
                                resultSet.getDate("registration").toLocalDate());
                LogBook logBook = new LogBook(resultSet.getInt("id"), resultSet.getDate("date").toLocalDate(), habit);
                logBooks.add(logBook);
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
        return logBooks;
    }

    @Override
    public List<LogBook> findAll() {
        List<LogBook> logBooks = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        if (!dbConnection.connect()) {
            logger.addRecord("Ошибка подключения к базе при поиске записей в журнале", false);
            return logBooks;
        }
        String sql = String.format("SELECT * FROM %s l JOIN %s h ON l.habit_id = h.id JOIN %s p ON h.person_id = p.id",nameTable,habitTable,personTable);
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
                Habit habit = new Habit(resultSet.getInt("habit_id"), 
                                resultSet.getString("name_habit"),
                                resultSet.getString("description"),
                                person, 
                                Period.values()[resultSet.getInt("period_id")],
                                resultSet.getDate("registration").toLocalDate());
                LogBook logBook = new LogBook(resultSet.getInt("id"), resultSet.getDate("date").toLocalDate(), habit);
                logBooks.add(logBook);
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
        return logBooks;
    }

    @Override
    public List<LogBook> findByHabit(Habit habit) {
        List<LogBook> logBooks = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        if (!dbConnection.connect()) {
            logger.addRecord("Ошибка подключения к базе при поиске записей в журнале", false);
            return logBooks;
        }
        String sql = String.format("SELECT * FROM %s WHERE habit_id = ? ",nameTable);
        try {
            connection = dbConnection.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, habit.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                LogBook logBook = new LogBook(resultSet.getInt("id"), resultSet.getDate("date").toLocalDate(), habit);
                logBooks.add(logBook);
            }
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
        return logBooks;
    }

}
