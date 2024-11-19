package ru.list.habitmanager;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.Mockito;

import ru.list.habitmanager.Model.Habit;
import ru.list.habitmanager.Model.LogBook;
import ru.list.habitmanager.Model.Period;
import ru.list.habitmanager.Model.Person;
import ru.list.habitmanager.Model.Role;
import ru.list.habitmanager.Repository.HabitRepository;
import ru.list.habitmanager.Repository.LogBookRepository;
import ru.list.habitmanager.Service.LogBookService;
import ru.list.habitmanager.Service.Implementation.LogBookServiceImplementation;

public class LogBookServiceTest {
    private static LogBookService logBookService;
    
    @Mock
    static LogBookRepository logBookRepositoryMockito;

    @Mock
    static HabitRepository habitRepositoryMockito;
            
    @BeforeAll
    public static void init() {
        logBookRepositoryMockito = Mockito.mock(LogBookRepository.class);
        habitRepositoryMockito = Mockito.mock(HabitRepository.class);
        logBookService = new LogBookServiceImplementation(logBookRepositoryMockito, habitRepositoryMockito);
    }

    @Test
    @DisplayName("Добавление новой записи")
    public void addLogBookTest() {
        Person person1 = new Person(1,"FirstPerson","email@server.ru","password",Role.ROLE_USER,true);
        Habit habit1 = new Habit(1,"Полезная привычка","Очень полезная привычка", person1, Period.daily,LocalDate.now());
        LogBook logBook = new LogBook(1,LocalDate.now(),habit1);

        Mockito.when(logBookRepositoryMockito.save(logBook)).thenReturn(true);

        assertThat(logBookService.addLogBook(logBook)).isTrue();
    }

    @Test
    @DisplayName("Удаление записи из журнала")
    public void deleteLogBookTest() {
        Person person1 = new Person(1,"FirstPerson","email@server.ru","password",Role.ROLE_USER,true);
        Habit habit1 = new Habit(1,"Полезная привычка","Очень полезная привычка", person1, Period.daily,LocalDate.now());
        LogBook logBook = new LogBook(1,LocalDate.now(),habit1);

        Mockito.when(logBookRepositoryMockito.delete(1)).thenReturn((true));

        assertThat(logBookService.deleteLogBook(logBook)).isTrue();

    }

    @Test
    @DisplayName("Удаление записи по ID")
    public void deleteLogBookByIdTest() {
        Mockito.when(logBookRepositoryMockito.delete(1)).thenReturn((true));

        assertThat(logBookService.deleteLogBookById(1)).isTrue();
    }

    @Test
    @DisplayName("Получение списка записей привычек")
    public void getLogBooksTest() {
        Mockito.when(logBookRepositoryMockito.findAll()).thenReturn(getLogBooks());

        assertThat(logBookService.getLogBooks().size()).isEqualTo(4);
    }

    @Test
    @DisplayName("Получение записи по ID")
    public void getLogBooksByIdTest() {
        Mockito.when(logBookRepositoryMockito.findById(2)).thenReturn(getLogBooks().get(1));

        assertThat(logBookService.getLogBookById(2).getHabit().getName()).isEqualTo("Вторая полезная привычка");
    }

    @Test
    @DisplayName("получение списка записей для привычки")
    public void getLogBookByHabitTest() {
        Habit habit = getLogBooks().get(1).getHabit();
        Mockito.when(habitRepositoryMockito.findById(habit.getId())).thenReturn(habit);
        Mockito.when(logBookRepositoryMockito.findByHabit(habit)).thenReturn(getLogBooks().stream().filter(l -> l.getHabit().equals(habit)).toList());

        assertThat(logBookService.getLogBookByHabit(habit.getId()).size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Получение списка записей по пользователю")
    public void getLogBookByPersonTest() {
        Mockito.when(logBookRepositoryMockito.findAll()).thenReturn(getLogBooks());

        assertThat(logBookService.getLogBookByPerson(2).size()).isEqualTo(2);
    }

    private List<LogBook> getLogBooks() {
        Person person1 = new Person(1,"FirstPerson","email@server.ru","password",Role.ROLE_USER,true);
        Habit habit1 = new Habit(1,"Первая полезная привычка","Очень полезная привычка", person1, Period.daily,LocalDate.now());
        Habit habit2 = new Habit(2,"Вторая полезная привычка","Очень полезная привычка", person1, Period.daily,LocalDate.now());

        Person person2 = new Person(2,"SecondPerson","box@server.ru","word",Role.ROLE_USER,true);
        Habit habit3 = new Habit(3,"Третья полезная привычка","Очень полезная привычка", person2, Period.daily,LocalDate.now());
        Habit habit4 = new Habit(4,"Четвертая полезная привычка","Очень полезная привычка", person2, Period.daily,LocalDate.now());

        LogBook logBook1 = new LogBook(1,LocalDate.now(),habit1);
        LogBook logBook2 = new LogBook(2,LocalDate.now(),habit2);
        LogBook logBook3 = new LogBook(3,LocalDate.now(),habit3);
        LogBook logBook4 = new LogBook(4,LocalDate.now(),habit4);

        return List.of(logBook1, logBook2, logBook3, logBook4);

    }

}
