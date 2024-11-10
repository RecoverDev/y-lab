package ru.list.habitmanager;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
import ru.list.habitmanager.Model.Person;
import ru.list.habitmanager.Model.Role;
import ru.list.habitmanager.Repository.PersonRepository;
import ru.list.habitmanager.Repository.DBImplementation.PersonRepositoryDBImplementation;

import org.testcontainers.junit.jupiter.Container;

@Testcontainers
public class PersonRepositoryDBTest {


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
    @DisplayName("Добавление пользователя")
    public void PersonRepositoryAddPerson() throws SQLException {
        boolean result = false;
        
        try(Connection connection = DriverManager.getConnection(database.getJdbcUrl(), database.getUsername(), database.getPassword())) {
            dbConnectionMockito = Mockito.mock(DataSource.class);
            Mockito.when(dbConnectionMockito.getConnection()).thenReturn(connection);

            PersonRepository repository = new PersonRepositoryDBImplementation(dbConnectionMockito);
            Person person = new Person(0,"Test User4", "user4@server.com", "222", Role.ROLE_USER, true);
    
            result = repository.save(person);
        }  catch (SQLException e) {
            System.out.println("Ошибка создания подключения к БД");
        }

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Удаление пользователя")
    public void PersonRepositoryDeletePerson() {
        boolean result = false;
        
        try(Connection connection = DriverManager.getConnection(database.getJdbcUrl(), database.getUsername(), database.getPassword())) {
            dbConnectionMockito = Mockito.mock(DataSource.class);
            Mockito.when(dbConnectionMockito.getConnection()).thenReturn(connection);

            PersonRepository repository = new PersonRepositoryDBImplementation(dbConnectionMockito);
            Person person = new Person(2,"Second User", "second@server.com", "222", Role.ROLE_USER, true);
    
            result = repository.delete(person.getId());
        }  catch (SQLException e) {
            System.out.println("Ошибка создания подключения к БД");
        }

        assertThat(result).isTrue();
    }

    @SuppressWarnings("null")
    @Test
    @DisplayName("Получение пользователя по E-mail и паролю")
    public void PersonRepositoryFindByEmailAndPasswordTest() {
        Person person = null;

        try(Connection connection = DriverManager.getConnection(database.getJdbcUrl(), database.getUsername(), database.getPassword())) {
            dbConnectionMockito = Mockito.mock(DataSource.class);
            Mockito.when(dbConnectionMockito.getConnection()).thenReturn(connection);

            PersonRepository repository = new PersonRepositoryDBImplementation(dbConnectionMockito);
    
            person = repository.findByEmailAndPassword("second@server.com", "222");
        }  catch (SQLException e) {
            System.out.println("Ошибка создания подключения к БД");
        }

        assertThat(person.getUsername()).hasToString("Second User");
    }

    @SuppressWarnings("null")
    @Test
    @DisplayName("Поиск пользователя по паролю")
    public void PersonRepositoryFindByPasswordTest() {
        Person person = null;

        try(Connection connection = DriverManager.getConnection(database.getJdbcUrl(), database.getUsername(), database.getPassword())) {
            dbConnectionMockito = Mockito.mock(DataSource.class);
            Mockito.when(dbConnectionMockito.getConnection()).thenReturn(connection);

            PersonRepository repository = new PersonRepositoryDBImplementation(dbConnectionMockito);
    
            person = repository.findByPassword("111");
        }  catch (SQLException e) {
            System.out.println("Ошибка создания подключения к БД");
        }

        assertThat(person.getUsername()).hasToString("First User");
    }

    @SuppressWarnings("null")
    @Test
    @DisplayName("Получение полного списка пользователей")
    public void PersonRepositoryFindAllTest() {
        List<Person> persons = null;

        try(Connection connection = DriverManager.getConnection(database.getJdbcUrl(), database.getUsername(), database.getPassword())) {
            dbConnectionMockito = Mockito.mock(DataSource.class);
            Mockito.when(dbConnectionMockito.getConnection()).thenReturn(connection);

            PersonRepository repository = new PersonRepositoryDBImplementation(dbConnectionMockito);
    
            persons = repository.findAll();
        }  catch (SQLException e) {
            System.out.println("Ошибка создания подключения к БД");
        }

        assertThat(persons.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Проверяем, существует ли такой пользователь")
    public void PersonRepositoryExistPersonTest() {
        boolean result = false;
        
        try(Connection connection = DriverManager.getConnection(database.getJdbcUrl(), database.getUsername(), database.getPassword())) {
            dbConnectionMockito = Mockito.mock(DataSource.class);
            Mockito.when(dbConnectionMockito.getConnection()).thenReturn(connection);

            PersonRepository repository = new PersonRepositoryDBImplementation(dbConnectionMockito);
            Person person = new Person(1,"First User", "first@server.com", "111", Role.ROLE_USER, true);
    
            result = repository.exist(person);
        }  catch (SQLException e) {
            System.out.println("Ошибка создания подключения к БД");
        }

        assertThat(result).isTrue();
    }

}
