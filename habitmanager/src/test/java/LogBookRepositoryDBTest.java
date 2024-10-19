import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import ru.list.Model.Habit;
import ru.list.Model.LogBook;
import ru.list.Model.Period;
import ru.list.Model.Person;
import ru.list.Repository.LogBookRepository;
import ru.list.Repository.DBImplementation.LogBookRepositoryDBImplementation;

@Testcontainers
public class LogBookRepositoryDBTest {

    @Container
    private static PostgreSQLContainer<?> database = new PostgreSQLContainer<>("postgres:14")
                    .withDatabaseName("habit_test")
                    .withUsername("postgres")
                    .withPassword("password")
                    .withInitScript("LogBookTestData.sql");

    static {
        database.start();;
    }

    @Test
    @DisplayName("Добавление новой записи в журнал")
    public void LogBookRepositoryAddlogBookTest() {
        boolean result = false;
        
        try(Connection connection = DriverManager.getConnection(database.getJdbcUrl(), database.getUsername(), database.getPassword())) {
            LogBookRepository repository = new LogBookRepositoryDBImplementation(connection);
            Person person = new Person(1,"Test User", "user1@server.com", "111", 0, true);
            Habit habit = new Habit(2,"call mom","call mom every day",person,Period.daily,LocalDate.of(2014,10,01));
            LogBook logBook = new LogBook(0,LocalDate.now(),habit);
    
            result = repository.save(logBook);
        }  catch (SQLException e) {
            System.out.println("Ошибка создания подключения к БД");
        }

        assertThat(result).isTrue();

    }

    @Test
    @DisplayName("Удаление записи из журнала")
    public void LogBookRepositoryDeleteLogBookTest() {
        boolean result = false;
        
        try(Connection connection = DriverManager.getConnection(database.getJdbcUrl(), database.getUsername(), database.getPassword())) {
            LogBookRepository repository = new LogBookRepositoryDBImplementation(connection);
            Person person = new Person(1,"Test User", "user1@server.com", "111", 0, true);
            Habit habit = new Habit(2,"call mom","call mom every day",person,Period.daily,LocalDate.of(2014,10,01));
            LogBook logBook = new LogBook(6,LocalDate.now(),habit);
    
            result = repository.delete(logBook);
        }  catch (SQLException e) {
            System.out.println("Ошибка создания подключения к БД");
        }

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Получение записей пользователя")
    public void LogBookRepositoryFindByPersonTest() {
        List<LogBook> logBooks = null;

        try(Connection connection = DriverManager.getConnection(database.getJdbcUrl(), database.getUsername(), database.getPassword())) {
            LogBookRepository repository = new LogBookRepositoryDBImplementation(connection);
            Person person = new Person(1,"Test User", "user1@server.com", "111", 0, true);
    
            logBooks = repository.findByPerson(person);
        }  catch (SQLException e) {
            System.out.println("Ошибка создания подключения к БД");
        }

        assertThat(7).isEqualTo(logBooks.size());
    }

    @Test
    @DisplayName("Получение всех записей журнала")
    public void LogBookRepositoryFindAllTest() {
        List<LogBook> logBooks = null;

        try(Connection connection = DriverManager.getConnection(database.getJdbcUrl(), database.getUsername(), database.getPassword())) {
            LogBookRepository repository = new LogBookRepositoryDBImplementation(connection);
    
            logBooks = repository.findAll();
        }  catch (SQLException e) {
            System.out.println("Ошибка создания подключения к БД");
        }

        assertThat(12).isEqualTo(logBooks.size());
    }

    @Test
    @DisplayName("Получение записей определенной привычки")
    public void LogBookRepositoryFindByHabitTest() {
        List<LogBook> logBooks = null;

        try(Connection connection = DriverManager.getConnection(database.getJdbcUrl(), database.getUsername(), database.getPassword())) {
            LogBookRepository repository = new LogBookRepositoryDBImplementation(connection);
            Person person = new Person(1,"Test User", "user1@server.com", "111", 0, true);
            Habit habit = new Habit(2,"call mom","call mom every day",person,Period.daily,LocalDate.of(2014,10,01));
    
            logBooks = repository.findByHabit(habit);
        }  catch (SQLException e) {
            System.out.println("Ошибка создания подключения к БД");
        }

        assertThat(2).isEqualTo(logBooks.size());
    }

}
