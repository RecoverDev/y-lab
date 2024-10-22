import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import ru.list.Db.DBConnection;
import ru.list.Model.Person;
import ru.list.Repository.PersonRepository;
import ru.list.Repository.DBImplementation.PersonRepositoryDBImplementation;
import ru.list.logger.Logger;

import org.testcontainers.junit.jupiter.Container;

@Testcontainers
public class PersonRepositoryDBTest {


    @SuppressWarnings("resource")
    @Container
    private static PostgreSQLContainer<?> database = new PostgreSQLContainer<>("postgres:14")
                    .withDatabaseName("habit_test")
                    .withUsername("postgres")
                    .withPassword("password")
                    .withInitScript("PersonTestData.sql");

    static {
        database.start();;
    }

    @Mock
    DBConnection dbConnectionMockito;

    @Mock
    Logger loggerMockito;

    @Test
    @DisplayName("Добавление пользователя")
    public void PersonRepositoryAddPerson() throws SQLException {
        boolean result = false;
        
        try(Connection connection = DriverManager.getConnection(database.getJdbcUrl(), database.getUsername(), database.getPassword())) {
            dbConnectionMockito = Mockito.mock(DBConnection.class);
            Mockito.when(dbConnectionMockito.getConnection()).thenReturn(connection);

            PersonRepository repository = new PersonRepositoryDBImplementation(dbConnectionMockito,loggerMockito);
            Person person = new Person(0,"Test User4", "user4@server.com", "222", 0, true);
    
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
            dbConnectionMockito = Mockito.mock(DBConnection.class);
            Mockito.when(dbConnectionMockito.getConnection()).thenReturn(connection);

            PersonRepository repository = new PersonRepositoryDBImplementation(dbConnectionMockito, loggerMockito);
            Person person = new Person(2,"Test User2", "user2@server.com", "222", 0, true);
    
            result = repository.delete(person);
        }  catch (SQLException e) {
            System.out.println("Ошибка создания подключения к БД");
        }

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Получение пользователя по E-mail и паролю")
    public void PersonRepositoryFindByEmailAndPasswordTest() {
        Person person = null;

        try(Connection connection = DriverManager.getConnection(database.getJdbcUrl(), database.getUsername(), database.getPassword())) {
            dbConnectionMockito = Mockito.mock(DBConnection.class);
            Mockito.when(dbConnectionMockito.getConnection()).thenReturn(connection);

            PersonRepository repository = new PersonRepositoryDBImplementation(dbConnectionMockito, loggerMockito);
    
            person = repository.findByEmailAndPassword("admin@server.com", "1234");
        }  catch (SQLException e) {
            System.out.println("Ошибка создания подключения к БД");
        }

        assertThat(person.getName()).hasToString("Test User3");
    }

    @Test
    @DisplayName("Поиск пользователя по паролю")
    public void PersonRepositoryFindByPasswordTest() {
        Person person = null;

        try(Connection connection = DriverManager.getConnection(database.getJdbcUrl(), database.getUsername(), database.getPassword())) {
            dbConnectionMockito = Mockito.mock(DBConnection.class);
            Mockito.when(dbConnectionMockito.getConnection()).thenReturn(connection);

            PersonRepository repository = new PersonRepositoryDBImplementation(dbConnectionMockito, loggerMockito);
    
            person = repository.findByPassword("111");
        }  catch (SQLException e) {
            System.out.println("Ошибка создания подключения к БД");
        }

        assertThat(person.getName()).hasToString("Test User");
    }

    @Test
    @DisplayName("Получение полного списка пользователей")
    public void PersonRepositoryFindAllTest() {
        List<Person> persons = null;

        try(Connection connection = DriverManager.getConnection(database.getJdbcUrl(), database.getUsername(), database.getPassword())) {
            dbConnectionMockito = Mockito.mock(DBConnection.class);
            Mockito.when(dbConnectionMockito.getConnection()).thenReturn(connection);

            PersonRepository repository = new PersonRepositoryDBImplementation(dbConnectionMockito, loggerMockito);
    
            persons = repository.findAll();
        }  catch (SQLException e) {
            System.out.println("Ошибка создания подключения к БД");
        }

        assertThat(persons.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("Проверяем, существует ли такой пользователь")
    public void PersonRepositoryExistPersonTest() {
        boolean result = false;
        
        try(Connection connection = DriverManager.getConnection(database.getJdbcUrl(), database.getUsername(), database.getPassword())) {
            dbConnectionMockito = Mockito.mock(DBConnection.class);
            Mockito.when(dbConnectionMockito.getConnection()).thenReturn(connection);

            PersonRepository repository = new PersonRepositoryDBImplementation(dbConnectionMockito, loggerMockito);
            Person person = new Person(2,"Test User2", "user2@server.com", "222", 0, true);
    
            result = repository.exist(person);
        }  catch (SQLException e) {
            System.out.println("Ошибка создания подключения к БД");
        }

        assertThat(result).isTrue();
    }

}
