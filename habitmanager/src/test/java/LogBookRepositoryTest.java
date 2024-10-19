import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ru.list.Model.Habit;
import ru.list.Model.LogBook;
import ru.list.Model.Period;
import ru.list.Model.Person;
import ru.list.Repository.LogBookRepository;
import ru.list.Repository.Implementation.LogBookRepositoryImplementation;

public class LogBookRepositoryTest {

    @Test
    @DisplayName("Добавление записи о выполнении привычки")
    public void LogBookRepositoryAddlogBook() {
        LogBookRepository repository = new LogBookRepositoryImplementation();

        Person person1 = new Person(1,"FirstPerson","email@server.ru","password",0,true);
        Habit habit1 = new Habit(1,"Полезная привычка","Очень полезная привычка", person1, Period.daily,LocalDate.now());

        LogBook logBook = new LogBook(1,LocalDate.now(),habit1);

        assertThat(repository.save(logBook)).isTrue();
        assertThat(1).isEqualTo(repository.findAll().size());
    }

    @Test
    @DisplayName("Удаление записи о выполнгении привычки")
    public void LogBookRepositoryDeleteLogBook() {
        LogBookRepository repository = new LogBookRepositoryImplementation();
        Person person = new Person(1,"FirstPerson","email@server.ru","password",0,true);
        Habit habit1 = new Habit(1,"Полезная привычка","Очень полезная привычка", person, Period.daily,LocalDate.now());
        Habit habit2 = new Habit(2,"Вторая полезная привычка","Очень полезная привычка", person, Period.daily,LocalDate.now());

        LogBook logBook1 = new LogBook(1,LocalDate.now(),habit1);
        LogBook logBook2 = new LogBook(2,LocalDate.now(),habit2);

        repository.save(logBook1);
        repository.save(logBook2);

        assertThat(repository.delete(logBook1)).isTrue();
        assertThat(1).isEqualTo(repository.findAll().size());
    }

    @Test
    @DisplayName("Получение списка записей выполнения привычек пользователя")
    public void LogBookRepositoryByPerson() {
        LogBookRepository repository = new LogBookRepositoryImplementation();
        Person person1 = new Person(1,"FirstPerson","email@server.ru","password",0,true);
        Habit habit1 = new Habit(1,"Первая полезная привычка","Очень полезная привычка", person1, Period.daily,LocalDate.now());
        Habit habit2 = new Habit(2,"Вторая полезная привычка","Очень полезная привычка", person1, Period.daily,LocalDate.now());

        Person person2 = new Person(2,"SecondPerson","box@server.ru","word",0,true);
        Habit habit3 = new Habit(3,"Третья полезная привычка","Очень полезная привычка", person2, Period.daily,LocalDate.now());
        Habit habit4 = new Habit(4,"Четвертая полезная привычка","Очень полезная привычка", person2, Period.daily,LocalDate.now());

        LogBook logBook1 = new LogBook(1,LocalDate.now(),habit1);
        LogBook logBook2 = new LogBook(2,LocalDate.now(),habit2);
        LogBook logBook3 = new LogBook(3,LocalDate.now(),habit3);
        LogBook logBook4 = new LogBook(4,LocalDate.now(),habit4);

        repository.save(logBook1);
        repository.save(logBook2);
        repository.save(logBook3);
        repository.save(logBook4);

        List<LogBook> logBooks = repository.findByPerson(person2);

        assertThat(2).isEqualTo(logBooks.size());
        assertThat("Третья полезная привычка").isEqualTo(logBooks.get(0).getHabit().getName());
    }

    @Test
    @DisplayName("Получение полного списка записей о выполнении привычек")
    public void LogBookRepositoryFindAll() {
        LogBookRepository repository = new LogBookRepositoryImplementation();
        Person person1 = new Person(1,"FirstPerson","email@server.ru","password",0,true);
        Habit habit1 = new Habit(1,"Первая полезная привычка","Очень полезная привычка", person1, Period.daily,LocalDate.now());
        Habit habit2 = new Habit(2,"Вторая полезная привычка","Очень полезная привычка", person1, Period.daily,LocalDate.now());

        Person person2 = new Person(2,"SecondPerson","box@server.ru","word",0,true);
        Habit habit3 = new Habit(3,"Третья полезная привычка","Очень полезная привычка", person2, Period.daily,LocalDate.now());
        Habit habit4 = new Habit(4,"Четвертая полезная привычка","Очень полезная привычка", person2, Period.daily,LocalDate.now());

        LogBook logBook1 = new LogBook(1,LocalDate.now(),habit1);
        LogBook logBook2 = new LogBook(2,LocalDate.now(),habit2);
        LogBook logBook3 = new LogBook(3,LocalDate.now(),habit3);
        LogBook logBook4 = new LogBook(4,LocalDate.now(),habit4);

        repository.save(logBook1);
        repository.save(logBook2);
        repository.save(logBook3);
        repository.save(logBook4);

        List<LogBook> logBooks = repository.findAll();

        assertThat(4).isEqualTo(logBooks.size());
    }

}
