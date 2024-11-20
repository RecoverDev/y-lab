package ru.list.habitmanager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ru.list.habitmanager.Controller.StatisticRestController;
import ru.list.habitmanager.DTO.LogBookResponse;
import ru.list.habitmanager.Mapper.HabitMapper;
import ru.list.habitmanager.Mapper.LogBookMapper;
import ru.list.habitmanager.Model.Habit;
import ru.list.habitmanager.Model.LogBook;
import ru.list.habitmanager.Model.Period;
import ru.list.habitmanager.Model.Person;
import ru.list.habitmanager.Model.Role;
import ru.list.habitmanager.Service.StatisticService;

public class StatisticRestControllerTest {
    private static MockMvc mockMvc;

    @Mock
    private static StatisticService statisticServiceMockito;

    @Mock
    private static LogBookMapper logBookMapperMockito;

    @Mock
    private static HabitMapper habitMapperMockito;

    @BeforeAll
    public static void init() {
        statisticServiceMockito = Mockito.mock(StatisticService.class);
        logBookMapperMockito = Mockito.mock(LogBookMapper.class);
        habitMapperMockito = Mockito.mock(HabitMapper.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new StatisticRestController(statisticServiceMockito, logBookMapperMockito, habitMapperMockito)).build();

    }

    @Test
    @DisplayName("Получение непрерывной последовательности выполнения привычки")
    public void getStreakByPersonTest() throws Exception {
        Mockito.when(statisticServiceMockito.streakHabits(1)).thenReturn(getLogBooks());
        Mockito.when(logBookMapperMockito.toListLogBookResponse(getLogBooks())).thenReturn(getLogBookResponses());

        mockMvc.perform(get("/statistic/streak/1")).andDo(print()).andExpect(status().isOk());

    }

    @Test
    @DisplayName("Получение процента выполнения привычки")
    public void getPercentByPersonTest() throws Exception {
        Mockito.when(statisticServiceMockito.percentSuccess(1)).thenReturn(56.0);
        MvcResult result = mockMvc.perform(get("/statistic/percent/1")).andDo(print()).andExpect(status().isOk()).andReturn();
        assertThat(result.getResponse().getContentAsString()).isEqualTo("56.0");
    } 

    @Test
    @DisplayName("Получение списка записей выполнения привычек за несколько последних дней")
    public void getExecutionHabitByPersonTest() throws Exception {
        Mockito.when(statisticServiceMockito.executionHabit(1, 10)).thenReturn(getLogBooks());
        Mockito.when(logBookMapperMockito.toListLogBookResponse(getLogBooks())).thenReturn(getLogBookResponses());

        mockMvc.perform(get("/statistic/execution/1/10")).andDo(print()).andExpect(status().isOk());
    }

    private List<LogBook> getLogBooks() {
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
        
        return List.of(logBook1, logBook2, logBook3, logBook4, logBook5, logBook6, logBook7, logBook8, logBook9, logBook10, logBook11);
    }

    private List<LogBookResponse> getLogBookResponses() {
        List<LogBookResponse> result = new ArrayList<>();

        List<LogBook> logBooks = getLogBooks();

        for (LogBook logBook : logBooks) {
            result.add(new LogBookResponse(logBook.getId(), logBook.getDate(), logBook.getHabit().getId()));
        }
        return result;
    }
}
