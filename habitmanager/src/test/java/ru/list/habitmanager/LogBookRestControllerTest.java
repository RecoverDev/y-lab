package ru.list.habitmanager;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ru.list.habitmanager.Controller.LogBookRestController;
import ru.list.habitmanager.DTO.LogBookResponse;
import ru.list.habitmanager.Mapper.LogBookMapper;
import ru.list.habitmanager.Model.Habit;
import ru.list.habitmanager.Model.LogBook;
import ru.list.habitmanager.Model.Period;
import ru.list.habitmanager.Model.Person;
import ru.list.habitmanager.Model.Role;
import ru.list.habitmanager.Service.LogBookService;

public class LogBookRestControllerTest {

    MockMvc mockMvc;

    @Mock
    LogBookService logBookServiceMockito;

    @Mock
    LogBookMapper logBookMapperMockito;

    @BeforeEach
    public void setup() {
        logBookServiceMockito = Mockito.mock(LogBookService.class);
        logBookMapperMockito = Mockito.mock(LogBookMapper.class);
        this.mockMvc = MockMvcBuilders.standaloneSetup(new LogBookRestController(logBookServiceMockito, logBookMapperMockito)).build();
    }

    @Test
    @DisplayName("получение списка всех записей о выполнении привычек")
    public void getLogBooksTest() throws Exception {
        Person person = new Person(1,"FirstPerson","email@server.ru","password", Role.ROLE_USER,true);
        Habit habit1 = new Habit(1,"Полезная привычка","Очень полезная привычка", person, Period.daily,LocalDate.now());
        Habit habit2 = new Habit(2,"Вторая полезная привычка","Очень полезная привычка", person, Period.daily,LocalDate.now());

        LogBook logBook1 = new LogBook(1,LocalDate.now(),habit1);
        LogBook logBook2 = new LogBook(2,LocalDate.now(),habit2);
        LogBookResponse logBookResponse1 = new LogBookResponse(1, LocalDate.now(), 1);
        LogBookResponse logBookResponse2 = new LogBookResponse(2, LocalDate.now(), 2);

        Mockito.when(logBookServiceMockito.getLogBooks()).thenReturn(List.of(logBook1,logBook2));
        Mockito.when(logBookMapperMockito.toListLogBookResponse(List.of(logBook1,logBook2))).thenReturn(List.of(logBookResponse1,logBookResponse2));

        mockMvc.perform(get("/logbooks")).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(2));

    }

    @Test
    @DisplayName("Получение записи по ID")
    public void getLogBookByIdTest() throws Exception {
        Person person = new Person(1,"FirstPerson","email@server.ru","password", Role.ROLE_USER,true);
        Habit habit1 = new Habit(1,"Полезная привычка","Очень полезная привычка", person, Period.daily,LocalDate.now());
        LogBook logBook1 = new LogBook(1,LocalDate.now(),habit1);
        LogBookResponse logBookResponse1 = new LogBookResponse(1, LocalDate.now(), 1);

        Mockito.when(logBookServiceMockito.getLogBookById(1)).thenReturn(logBook1);
        Mockito.when(logBookMapperMockito.toLogBookResponse(logBook1)).thenReturn(logBookResponse1);

        mockMvc.perform(get("/logbooks/1")).andExpect(status().isOk());
    }
}
