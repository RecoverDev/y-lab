import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import ru.list.Model.Habit;
import ru.list.Model.LogBook;
import ru.list.Model.Period;
import ru.list.Model.Person;
import ru.list.Repository.HabitRepository;
import ru.list.Repository.LogBookRepository;
import ru.list.Service.StatisticService;
import ru.list.Service.Implementation.StatisticServiceImplementation;

public class StatisticServiceTest {

    @Mock
    HabitRepository habitRepositoryMockito;

    @Mock
    LogBookRepository logBookRepositoryMockito;

    @Test
    @DisplayName("Получение непрерывной последовательности выполнения привычек")
    public void streakHabitsTest() {
        Person person = new Person(1,"Test User","user@server.com","password",0,true);
        
        Habit habit1 = new Habit(1,"Read book", "read book everyday",person,Period.daily,LocalDate.of(2024, 10,1));
        Habit habit2 = new Habit(2,"yoga", "yoga", person, Period.weekly, LocalDate.of(2024, 10,2));

        LogBook logBook1 = new LogBook(LocalDate.of(2024, 10, 1), habit1);
        LogBook logBook2 = new LogBook(LocalDate.of(2024, 10, 2), habit1);
        LogBook logBook3 = new LogBook(LocalDate.of(2024, 10, 3), habit1);
        LogBook logBook4 = new LogBook(LocalDate.of(2024, 10, 4), habit1);
        LogBook logBook5 = new LogBook(LocalDate.of(2024, 10, 5), habit1);
        LogBook logBook6 = new LogBook(LocalDate.of(2024, 10, 6), habit1);
        LogBook logBook7 = new LogBook(LocalDate.of(2024, 10, 7), habit1);
        LogBook logBook8 = new LogBook(LocalDate.of(2024, 10, 10), habit1);
        LogBook logBook9 = new LogBook(LocalDate.of(2024, 10, 11), habit1);

        LogBook logBook10 = new LogBook(LocalDate.of(2024, 10, 2), habit2);
        LogBook logBook11 = new LogBook(LocalDate.of(2024, 10, 9), habit2);

        habitRepositoryMockito = Mockito.mock(HabitRepository.class);
        Mockito.when(habitRepositoryMockito.findByPerson(person)).thenReturn(List.of(habit1,habit2));

        logBookRepositoryMockito = Mockito.mock(LogBookRepository.class);
        Mockito.when(logBookRepositoryMockito.findByPerson(person))
               .thenReturn(List.of(logBook1, logBook2, logBook3, logBook4, logBook5, logBook6, logBook7, logBook8, logBook9, logBook10, logBook11));

        StatisticService statisticService = new StatisticServiceImplementation(habitRepositoryMockito, logBookRepositoryMockito);

        Assertions.assertEquals(4, statisticService.streakHabits(person).size());
    }

    @Test
    @DisplayName("Вычисление процента успешного выполнения привычки")
    public void percentSuccessTest() {
        Person person = new Person(2,"Test User","user@server.com","password",0,true);
        
        Habit habit1 = new Habit(1,"Read book", "read book everyday",person,Period.daily,LocalDate.of(2024, 10,1));
        Habit habit2 = new Habit(2,"yoga", "yoga", person, Period.weekly, LocalDate.of(2024, 10,2));

        LogBook logBook1 = new LogBook(LocalDate.of(2024, 10, 1), habit1);
        LogBook logBook2 = new LogBook(LocalDate.of(2024, 10, 2), habit1);
        LogBook logBook3 = new LogBook(LocalDate.of(2024, 10, 3), habit1);
        LogBook logBook4 = new LogBook(LocalDate.of(2024, 10, 4), habit1);
        LogBook logBook5 = new LogBook(LocalDate.of(2024, 10, 5), habit1);
        LogBook logBook6 = new LogBook(LocalDate.of(2024, 10, 6), habit1);
        LogBook logBook7 = new LogBook(LocalDate.of(2024, 10, 7), habit1);
        LogBook logBook8 = new LogBook(LocalDate.of(2024, 10, 10), habit1);
        LogBook logBook9 = new LogBook(LocalDate.of(2024, 10, 11), habit1);

        LogBook logBook10 = new LogBook(LocalDate.of(2024, 10, 2), habit2);
        LogBook logBook11 = new LogBook(LocalDate.of(2024, 10, 9), habit2);

        habitRepositoryMockito = Mockito.mock(HabitRepository.class);
        Mockito.when(habitRepositoryMockito.findByPerson(person)).thenReturn(List.of(habit1,habit2));

        logBookRepositoryMockito = Mockito.mock(LogBookRepository.class);
        Mockito.when(logBookRepositoryMockito.findByPerson(person))
               .thenReturn(List.of(logBook1, logBook2, logBook3, logBook4, logBook5, logBook6, logBook7, logBook8, logBook9, logBook10, logBook11));

        StatisticService statisticService = new StatisticServiceImplementation(habitRepositoryMockito, logBookRepositoryMockito);
        Assertions.assertEquals(79, Math.ceil(statisticService.percentSuccess(person)));
    }

    @Test
    @DisplayName("Прогресс выполнения")
    public void progressHabitTest() {
        Person person = new Person(1,"Test User","user@server.com","password",0,true);
        
        Habit habit1 = new Habit(1,"Read book", "read book everyday",person,Period.daily,LocalDate.of(2024, 10,1));
        Habit habit2 = new Habit(2,"yoga", "yoga", person, Period.weekly, LocalDate.of(2024, 10,2));

        LogBook logBook1 = new LogBook(LocalDate.of(2024, 10, 1), habit1);
        LogBook logBook2 = new LogBook(LocalDate.of(2024, 10, 2), habit1);
        LogBook logBook3 = new LogBook(LocalDate.of(2024, 10, 3), habit1);
        LogBook logBook4 = new LogBook(LocalDate.of(2024, 10, 4), habit1);
        LogBook logBook5 = new LogBook(LocalDate.of(2024, 10, 5), habit1);
        LogBook logBook6 = new LogBook(LocalDate.of(2024, 10, 6), habit1);
        LogBook logBook7 = new LogBook(LocalDate.of(2024, 10, 7), habit1);
        LogBook logBook8 = new LogBook(LocalDate.of(2024, 10, 10), habit1);
        LogBook logBook9 = new LogBook(LocalDate.of(2024, 10, 11), habit1);

        LogBook logBook10 = new LogBook(LocalDate.of(2024, 10, 2), habit2);
        LogBook logBook11 = new LogBook(LocalDate.of(2024, 10, 9), habit2);

        habitRepositoryMockito = Mockito.mock(HabitRepository.class);
        Mockito.when(habitRepositoryMockito.findByPerson(person)).thenReturn(List.of(habit1,habit2));

        logBookRepositoryMockito = Mockito.mock(LogBookRepository.class);
        Mockito.when(logBookRepositoryMockito.findByPerson(person))
               .thenReturn(List.of(logBook1, logBook2, logBook3, logBook4, logBook5, logBook6, logBook7, logBook8, logBook9, logBook10, logBook11));

        StatisticService statisticService = new StatisticServiceImplementation(habitRepositoryMockito, logBookRepositoryMockito);
        Assertions.assertEquals(9, statisticService.progressHabit(person).get(habit1));
        
    }


}
