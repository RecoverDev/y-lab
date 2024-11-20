package ru.list.habitmanager;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import ru.list.habitmanager.Model.Habit;
import ru.list.habitmanager.Model.Period;
import ru.list.habitmanager.Model.Person;
import ru.list.habitmanager.Model.Role;
import ru.list.habitmanager.Repository.HabitRepository;
import ru.list.habitmanager.Repository.DBImplementation.HabitRepositoryDBImplementation;

import org.testcontainers.junit.jupiter.Container;

@Testcontainers
public class HabitRepositoryDBTest {

    @SuppressWarnings("resource")
    @Container
    private static PostgreSQLContainer<?> database = new PostgreSQLContainer<>("postgres:14")
                    .withDatabaseName("habit_test")
                    .withUsername("postgres")
                    .withPassword("password");

    static {
        database.start();
        InitializateBase();
    }

    private static void InitializateBase() {
        try (Connection connection = DriverManager.getConnection(database.getJdbcUrl(), database.getUsername(), database.getPassword())) {
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new Liquibase("db/changelog/changelog.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update();
            liquibase.close();
        } catch (SQLException | LiquibaseException e) {
            System.out.println("Ошибка миграции при создании контейнера");
        }
    }


    @Mock
    DataSource dbConnectionMockito;


    @Test
    @DisplayName("Добавляем новую привычку")
    public void HabitRepositoryAddHabitTest() {
        boolean result = false;

        try(Connection connection = DriverManager.getConnection(database.getJdbcUrl(), database.getUsername(), database.getPassword())) {
            dbConnectionMockito = Mockito.mock(DataSource.class);
            Mockito.when(dbConnectionMockito.getConnection()).thenReturn(connection);

            HabitRepository repository = new HabitRepositoryDBImplementation(dbConnectionMockito);
            Person person = new Person(2,"Test User2", "user2@server.com", "222",  Role.ROLE_USER, true);
            Habit habit = new Habit(0,"good habit", "very good habit", person,Period.daily,LocalDate.now());
    
            result = repository.save(habit);
        }  catch (SQLException e) {
            System.out.println("Ошибка создания подключения к БД");
        }

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Удаление привычки")
    public void HabitRepositoryDeleteHabitTest() {
        boolean result = false;
        try(Connection connection = DriverManager.getConnection(database.getJdbcUrl(), database.getUsername(), database.getPassword())) {
            dbConnectionMockito = Mockito.mock(DataSource.class);
            Mockito.when(dbConnectionMockito.getConnection()).thenReturn(connection);

            HabitRepository repository = new HabitRepositoryDBImplementation(dbConnectionMockito);
            Person person = new Person(1,"Test User", "user1@server.com", "111",  Role.ROLE_USER, true);
            Habit habit = new Habit(2,"call mom","call mom every day",person,Period.daily,LocalDate.of(2014,10,01));
    
            result = repository.delete(habit);
        }  catch (SQLException e) {
            System.out.println("Ошибка создания подключения к БД");
        }

        assertThat(result).isTrue();
    }

    @SuppressWarnings("null")
    @Test
    @DisplayName("Получение привычек пользователя")
    public void HabitRepositoryFindPersonHabitsTest() {
        List<Habit> habits = null;

        try(Connection connection = DriverManager.getConnection(database.getJdbcUrl(), database.getUsername(), database.getPassword())) {
            dbConnectionMockito = Mockito.mock(DataSource.class);
            Mockito.when(dbConnectionMockito.getConnection()).thenReturn(connection);
            
            HabitRepository repository = new HabitRepositoryDBImplementation(dbConnectionMockito);
            Person person = new Person(1,"Test User", "user1@server.com", "111", Role.ROLE_USER, true);
    
            habits = repository.findByPerson(person.getId());
        }  catch (SQLException e) {
            System.out.println("Ошибка создания подключения к БД");
        }

        assertThat(habits.size()).isEqualTo(1);
    }

    @SuppressWarnings("null")
    @Test
    @DisplayName("Получить полный список привычек")
    public void HabitRepositoryFindAllTest() {
        List<Habit> habits = null;

        try(Connection connection = DriverManager.getConnection(database.getJdbcUrl(), database.getUsername(), database.getPassword())) {
            dbConnectionMockito = Mockito.mock(DataSource.class);
            Mockito.when(dbConnectionMockito.getConnection()).thenReturn(connection);
            
            HabitRepository repository = new HabitRepositoryDBImplementation(dbConnectionMockito);
    
            habits = repository.findAll();
        }  catch (SQLException e) {
            System.out.println("Ошибка создания подключения к БД");
        }

        assertThat(habits.size()).isEqualTo(4);

    }

    @Test
    @DisplayName("Проверить существование привычки")
    public void HabitRepositoryExistHabitTest() {
        boolean result = false;
        try(Connection connection = DriverManager.getConnection(database.getJdbcUrl(), database.getUsername(), database.getPassword())) {
            dbConnectionMockito = Mockito.mock(DataSource.class);
            Mockito.when(dbConnectionMockito.getConnection()).thenReturn(connection);

            HabitRepository repository = new HabitRepositoryDBImplementation(dbConnectionMockito);
            Person person = new Person(1,"Test User", "user1@server.com", "111", Role.ROLE_USER, true);
            Habit habit = new Habit(2,"call mom","call mom every day",person,Period.daily,LocalDate.of(2014,10,01));
    
            result = repository.exist(habit);
        }  catch (SQLException e) {
            System.out.println("Ошибка создания подключения к БД");
        }

        assertThat(result).isTrue();
    }


}
