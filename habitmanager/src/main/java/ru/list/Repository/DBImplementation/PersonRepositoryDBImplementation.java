package ru.list.Repository.DBImplementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.list.Model.Person;
import ru.list.Repository.PersonRepository;

public class PersonRepositoryDBImplementation implements PersonRepository {
    private String nameTable = "person";
    private String nameSchema = "habit";
    private Connection connection = null;
    private String nameSerialID = "person_id_seq";

    public PersonRepositoryDBImplementation(Connection connection) {
        this.connection = connection;
        if (nameSchema.length() > 0) {
            nameTable = nameSchema + "." + nameTable;
            nameSerialID = String.format("%s.%s", nameSchema, nameSerialID); 
        }
    }

    @Override
    public boolean save(Person person) {
        boolean result;
        String sql = String.format("INSERT INTO %s (id, username, email, password, role, blocked) VALUES (nextval('%s'), ?, ?, ?, ?, ?)", nameTable,nameSerialID);
        try {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(sql);
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
                System.out.println("Ошибка подключения к БД: " + ex.getMessage());
            }
            result = false;
        } 

        return result;
    }

    @Override
    public boolean delete(Person person) {
        boolean result;
        String sql = "DELETE FROM " + nameTable + " WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, person.getId());
            int count = statement.executeUpdate();
            result = (count == 1);
        } catch(SQLException e) {
            result = false;
        }

        return result;
    }

    @Override
    public Person findByEmailAndPassword(String email, String password) {
        Person result = null;
        String sql = String.format("SELECT * FROM %s WHERE email = ? AND password = ?",nameTable);
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
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
        } catch(SQLException e) {
            result = null;
        }

        return result;
    }

    @Override
    public Person findByPassword(String password) {
        Person result = null;
        String sql = String.format("SELECT * FROM %s WHERE password = ?",nameTable);
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
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
        } catch(SQLException e) {
            result = null;
        }

        return result;
    }

    @Override
    public List<Person> findAll() {
        List<Person> result = new ArrayList<>();
        String sql = "SELECT * FROM " + nameTable;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
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
        } catch(SQLException e) {
            System.out.println("Ошибка подключения к БД: " + e.getMessage());
        }

        return result;
    }

    @Override
    public boolean exist(Person person) {
        boolean result = false;
        String sql = String.format("SELECT COUNT(*) FROM %s WHERE id = ?",nameTable);
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, person.getId());
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
