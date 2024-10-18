package ru.list.Repository.DBImplementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.list.Model.Habit;
import ru.list.Model.Period;
import ru.list.Model.Person;
import ru.list.Repository.HabitRepository;

public class HabitRepositoryDBImplementation implements HabitRepository {
    private String nameTable = "habit";
    private String nameSchema = "habit";
    private Connection connection = null;
    private String nameSerialID = "habit_id_seq";

    public HabitRepositoryDBImplementation(Connection connection) {
        this.connection = connection;
        if (nameSchema.length() > 0) {
            nameTable = nameSchema + "." + nameTable;
            nameSerialID = String.format("%s.%s", nameSchema, nameSerialID); 
        }
    }

    @Override
    public boolean save(Habit habit) {
        boolean result;
        String sql = String.format("INSERT INTO %s (id, name_habit, description, person_id, period_id, registration) VALUES (nextval('%s'), ?, ?, ?, ?, ?)", nameTable,nameSerialID);
        try {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(sql);
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

            }
            result = false;
        } 

        return result;
    }

    @Override
    public boolean delete(Habit habit) {
        boolean result;
        String sql = "DELETE FROM " + nameTable + " WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, habit.getId());
            int count = statement.executeUpdate();
            result = (count == 1);
        } catch(SQLException e) {
            result = false;
        }

        return result;
    }

    @Override
    public List<Habit> findByPerson(Person person) {
        List<Habit> habits = new ArrayList<>();
        String sql = "SELECT * FROM " + nameTable + " WHERE person_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
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
        } catch (SQLException e) {

        } 
        return habits;  
    }

    @Override
    public List<Habit> findAll() {
        List<Habit> habits = new ArrayList<>();

        String personTable = "habit.person";
        String sql = String.format("SELECT * FROM %s h JOIN %s p ON h.person_id = p.id",nameTable,personTable);
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
                Habit habit = new Habit(resultSet.getInt("id"), 
                                resultSet.getString("name_habit"),
                                resultSet.getString("description"),
                                person, 
                                Period.values()[resultSet.getInt("period_id")],
                                resultSet.getDate("registration").toLocalDate());
                habits.add(habit);
            }
        } catch (SQLException e) {

        }

        return habits;
    }

    @Override
    public boolean exist(Habit habit) {
        boolean result = false;
        String sql = String.format("SELECT COUNT(*) FROM %s WHERE id = ?",nameTable);
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, habit.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result = resultSet.getInt("COUNT") > 0;
            }
        } catch(SQLException e) {
            result = false;
        }

        return result;
    }

}
