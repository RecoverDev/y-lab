package ru.list.habitmanager;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import ru.list.habitmanager.Model.Habit;
import ru.list.habitmanager.Model.LogBook;
import ru.list.habitmanager.Model.Period;
import ru.list.habitmanager.Model.Person;
import ru.list.habitmanager.Model.Role;
import ru.list.habitmanager.Repository.HabitRepository;
import ru.list.habitmanager.Repository.LogBookRepository;
import ru.list.habitmanager.Service.HabitService;
import ru.list.habitmanager.Service.Implementation.HabitServiceImplementation;

public class HabitServiceTest {
    private static HabitService habitService;

    @Mock
    static LogBookRepository logBookRepositoryMockito;

    @Mock
    static HabitRepository habitRepositoryMockito;

    @BeforeAll
    public static void init() {
        logBookRepositoryMockito = Mockito.mock(LogBookRepository.class);
        habitRepositoryMockito = Mockito.mock(HabitRepository.class);
        habitService = new HabitServiceImplementation(habitRepositoryMockito, logBookRepositoryMockito);
    }

    @Test
    @DisplayName("Добавление новой привычки")
    public void addHabitTest() {
        Person person = new Person(1,"FirstPerson","email@server.ru","password",Role.ROLE_USER,true);
        Habit habit1 = new Habit(1,"Полезная привычка","Очень полезная привычка", person, Period.daily,LocalDate.now());        
        Mockito.when(habitRepositoryMockito.save(habit1)).thenReturn(true);
        assertThat(habitService.addHabit(habit1)).isTrue();
    }

    @Test
    @DisplayName("Удаление привычки")
    public void deleteHabitTest() {
        Person person = new Person(1,"FirstPerson","email@server.ru","password",Role.ROLE_USER,true);
        Habit habit1 = new Habit(1,"Полезная привычка","Очень полезная привычка", person, Period.daily,LocalDate.now());        
        LogBook logBook1 = new LogBook(1,LocalDate.now(),habit1);
        LogBook logBook2 = new LogBook(2,LocalDate.now(),habit1);

        Mockito.when(habitRepositoryMockito.findById(1)).thenReturn(habit1);
        Mockito.when(logBookRepositoryMockito.findByHabit(habit1)).thenReturn(List.of(logBook1,logBook2));
        Mockito.when(logBookRepositoryMockito.delete(1)).thenReturn(true);
        Mockito.when(logBookRepositoryMockito.delete(2)).thenReturn(true);

        assertThat(habitService.deleteHabit(1)).isTrue();
    }

    @Test
    @DisplayName("Получение списка привычек пользователя")
    public void getHabitByPersonTest() {
        Person person = new Person(1,"FirstPerson","email@server.ru","password",Role.ROLE_USER,true);
        Habit habit1 = new Habit(1,"Полезная привычка","Очень полезная привычка", person, Period.daily,LocalDate.now());        
        Habit habit2 = new Habit(2,"Вторая полезная привычка","Очень полезная привычка", person, Period.daily,LocalDate.now());

        Mockito.when(habitRepositoryMockito.findByPerson(person.getId())).thenReturn(List.of(habit1, habit2));

        assertThat(habitService.getHabitsByPerson(person.getId()).size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Получение привычки по ID")
    public void getHabitByIdTest() {
        Person person = new Person(1,"FirstPerson","email@server.ru","password",Role.ROLE_USER,true);
        Habit habit1 = new Habit(1,"Полезная привычка","Очень полезная привычка", person, Period.daily,LocalDate.now());        
        
        Mockito.when(habitRepositoryMockito.findById(1)).thenReturn(habit1);

        assertThat(habitService.getById(1)).isEqualTo(habit1);
    }

}
