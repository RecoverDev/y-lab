package ru.list.Repository.DBImplementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.list.Model.Habit;
import ru.list.Model.LogBook;
import ru.list.Model.Period;
import ru.list.Model.Person;
import ru.list.Repository.LogBookRepository;

public class LogBookRepositoryDBImplementation implements LogBookRepository {
    private String nameTable = "logbook";
    private String nameSchema = "habit";
    private Connection connection = null;
    private String nameSerialID = "logbook_id_seq";

    public LogBookRepositoryDBImplementation(Connection connection) {
        this.connection = connection;
        if (nameSchema.length() > 0) {
            nameTable = nameSchema + "." + nameTable;
            nameSerialID = String.format("%s.%s", nameSchema, nameSerialID); 
        }
    }


    @Override
    public boolean save(LogBook logBook) {
        boolean result;
        String sql = String.format("INSERT INTO %s (id, habit_id, date) VALUES (nextval('%s'), ?, ?)", nameTable,nameSerialID);
        try {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(sql);
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
                System.out.println("Ошибка подключения к БД: " + ex.getMessage());
            }
            result = false;
        } 

        return result;
    }

    @Override
    public boolean delete(LogBook logBook) {
        boolean result;
        String sql = "DELETE FROM " + nameTable + " WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, logBook.getId());
            int count = statement.executeUpdate();
            result = (count == 1);
        } catch(SQLException e) {
            result = false;
        }

        return result;
    }

    @Override
    public List<LogBook> findByPerson(Person person) {
        List<LogBook> logBooks = new ArrayList<>();
        String habitTable = "habit.habit";
        String sql = String.format("SELECT * FROM %s l JOIN %s h ON l.habit_id = h.id WHERE h.person_id = ? ",nameTable,habitTable);
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
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
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к БД: " + e.getMessage());
        }
        return logBooks;
    }

    @Override
    public List<LogBook> findAll() {
        List<LogBook> logBooks = new ArrayList<>();
        String habitTable = "habit.habit";
        String personTable = "habit.person";
        String sql = String.format("SELECT * FROM %s l JOIN %s h ON l.habit_id = h.id JOIN %s p ON h.person_id = p.id",nameTable,habitTable,personTable);
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
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
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к БД: " + e.getMessage());
        }
        return logBooks;
    }

    @Override
    public List<LogBook> findByHabit(Habit habit) {
        List<LogBook> logBooks = new ArrayList<>();
        String sql = String.format("SELECT * FROM %s WHERE habit_id = ? ",nameTable);
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, habit.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                LogBook logBook = new LogBook(resultSet.getInt("id"), resultSet.getDate("date").toLocalDate(), habit);
                logBooks.add(logBook);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к БД: " + e.getMessage());
        }
        return logBooks;
    }

}
