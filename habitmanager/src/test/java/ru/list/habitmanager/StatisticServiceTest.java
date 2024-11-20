package ru.list.habitmanager;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Assertions;
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
import ru.list.habitmanager.Service.StatisticService;
import ru.list.habitmanager.Service.Implementation.StatisticServiceImplementation;

public class StatisticServiceTest {

    @Mock
    HabitRepository habitRepositoryMockito;

    @Mock
    LogBookRepository logBookRepositoryMockito;

    @Test
    @DisplayName("Получение непрерывной последовательности выполнения привычек")
    public void streakHabitsTest() {
        Person person = new Person(1,"Test User","user@server.com","password",Role.ROLE_USER,true);
        
        Habit habit1 = new Habit(1,"Read book", "read book everyday",person,Period.daily,LocalDate.of(2024, 10,1));
        Habit habit2 = new Habit(2,"yoga", "yoga", person, Period.weekly, LocalDate.of(2024, 10,2));

        LogBook logBook1 = new LogBook(1,LocalDate.of(2024, 10, 1), habit1);
        LogBook logBook2 = new LogBook(2,LocalDate.of(2024, 10, 2), habit1);
        LogBook logBook3 = new LogBook(3,LocalDate.of(2024, 10, 3), habit1);
        LogBook logBook4 = new LogBook(4,LocalDate.of(2024, 10, 4), habit1);
        LogBook logBook5 = new LogBook(5,LocalDate.of(2024, 10, 5), habit1);
        LogBook logBook6 = new LogBook(6,LocalDate.of(2024, 10, 6), habit1);
        LogBook logBook7 = new LogBook(7,LocalDate.of(2024, 10, 7), habit1);
        LogBook logBook8 = new LogBook(8,LocalDate.of(2024, 10, 10), habit1);
        LogBook logBook9 = new LogBook(9,LocalDate.of(2024, 10, 11), habit1);

        LogBook logBook10 = new LogBook(10,LocalDate.of(2024, 10, 2), habit2);
        LogBook logBook11 = new LogBook(11,LocalDate.of(2024, 10, 9), habit2);

        habitRepositoryMockito = Mockito.mock(HabitRepository.class);
        Mockito.when(habitRepositoryMockito.findByPerson(person.getId())).thenReturn(List.of(habit1,habit2));

        logBookRepositoryMockito = Mockito.mock(LogBookRepository.class);
        Mockito.when(logBookRepositoryMockito.findByPerson(person.getId()))
               .thenReturn(List.of(logBook1, logBook2, logBook3, logBook4, logBook5, logBook6, logBook7, logBook8, logBook9, logBook10, logBook11));

        StatisticService statisticService = new StatisticServiceImplementation(habitRepositoryMockito, logBookRepositoryMockito);

        Assertions.assertEquals(4, statisticService.streakHabits(person.getId()).size());
    }

    @Test
    @DisplayName("Вычисление процента успешного выполнения привычки")
    public void percentSuccessTest() {
        Person person = new Person(2,"Test User","user@server.com","password",Role.ROLE_USER,true);
        
        Habit habit1 = new Habit(1,"Read book", "read book everyday",person,Period.daily,LocalDate.of(2024, 10,1));
        Habit habit2 = new Habit(2,"yoga", "yoga", person, Period.weekly, LocalDate.of(2024, 10,2));

        LogBook logBook1 = new LogBook(1,LocalDate.now().minusDays(10),  habit1);
        LogBook logBook2 = new LogBook(2,LocalDate.now().minusDays(9), habit1);
        LogBook logBook3 = new LogBook(3,LocalDate.now().minusDays(8), habit1);
        LogBook logBook4 = new LogBook(4,LocalDate.now().minusDays(7), habit1);
        LogBook logBook5 = new LogBook(5,LocalDate.now().minusDays(6), habit1);
        LogBook logBook6 = new LogBook(6,LocalDate.now().minusDays(5), habit1);
        LogBook logBook7 = new LogBook(7,LocalDate.now().minusDays(4), habit1);
        LogBook logBook8 = new LogBook(8,LocalDate.now().minusDays(3), habit1);
        LogBook logBook9 = new LogBook(9,LocalDate.now().minusDays(2), habit1);

        LogBook logBook10 = new LogBook(10,LocalDate.now().minusDays(10), habit2);
        LogBook logBook11 = new LogBook(11,LocalDate.now().minusDays(3), habit2);

        habitRepositoryMockito = Mockito.mock(HabitRepository.class);
        Mockito.when(habitRepositoryMockito.findByPerson(person.getId())).thenReturn(List.of(habit1,habit2));

        logBookRepositoryMockito = Mockito.mock(LogBookRepository.class);
        Mockito.when(logBookRepositoryMockito.findByPerson(person.getId()))
               .thenReturn(List.of(logBook1, logBook2, logBook3, logBook4, logBook5, logBook6, logBook7, logBook8, logBook9, logBook10, logBook11));

        StatisticService statisticService = new StatisticServiceImplementation(habitRepositoryMockito, logBookRepositoryMockito);
        Assertions.assertEquals(56, Math.ceil(statisticService.percentSuccess(person.getId())));
    }

    @Test
    @DisplayName("Прогресс выполнения")
    public void progressHabitTest() {
        Person person = new Person(1,"Test User","user@server.com","password",Role.ROLE_USER,true);
        
        Habit habit1 = new Habit(1,"Read book", "read book everyday",person,Period.daily,LocalDate.of(2024, 10,1));
        Habit habit2 = new Habit(2,"yoga", "yoga", person, Period.weekly, LocalDate.of(2024, 10,2));

        LogBook logBook1 = new LogBook(1,LocalDate.now().minusDays(10),  habit1);
        LogBook logBook2 = new LogBook(2,LocalDate.now().minusDays(9), habit1);
        LogBook logBook3 = new LogBook(3,LocalDate.now().minusDays(8), habit1);
        LogBook logBook4 = new LogBook(4,LocalDate.now().minusDays(7), habit1);
        LogBook logBook5 = new LogBook(5,LocalDate.now().minusDays(6), habit1);
        LogBook logBook6 = new LogBook(6,LocalDate.now().minusDays(5), habit1);
        LogBook logBook7 = new LogBook(7,LocalDate.now().minusDays(4), habit1);
        LogBook logBook8 = new LogBook(8,LocalDate.now().minusDays(3), habit1);
        LogBook logBook9 = new LogBook(9,LocalDate.now().minusDays(2), habit1);

        LogBook logBook10 = new LogBook(10,LocalDate.now().minusDays(10), habit2);
        LogBook logBook11 = new LogBook(11,LocalDate.now().minusDays(3), habit2);

        habitRepositoryMockito = Mockito.mock(HabitRepository.class);
        Mockito.when(habitRepositoryMockito.findByPerson(person.getId())).thenReturn(List.of(habit1,habit2));

        logBookRepositoryMockito = Mockito.mock(LogBookRepository.class);
        Mockito.when(logBookRepositoryMockito.findByPerson(person.getId()))
               .thenReturn(List.of(logBook1, logBook2, logBook3, logBook4, logBook5, logBook6, logBook7, logBook8, logBook9, logBook10, logBook11));

        StatisticService statisticService = new StatisticServiceImplementation(habitRepositoryMockito, logBookRepositoryMockito);
        Assertions.assertEquals(9, statisticService.progressHabit(person.getId()).get(habit1));
        
    }


}
