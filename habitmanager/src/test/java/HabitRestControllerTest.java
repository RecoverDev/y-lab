import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ru.list.Principal;
import ru.list.Controller.HabitRestController;
import ru.list.DTO.HabitResponse;
import ru.list.Mapper.HabitMapper;
import ru.list.Model.Habit;
import ru.list.Model.Period;
import ru.list.Model.Person;
import ru.list.Service.HabitService;
import ru.list.Service.PersonService;

public class HabitRestControllerTest {
    MockMvc mockMvc;

    @Mock
    HabitService habitServiceMockito;
    
    @Mock
    PersonService personServiceMockito;

    @Mock
    HabitMapper habitMapperMockito;

    @Mock
    Principal principalMockito;

    @BeforeEach
    public void setup() {
        habitServiceMockito = Mockito.mock(HabitService.class);
        personServiceMockito = Mockito.mock(PersonService.class);
        habitMapperMockito = Mockito.mock(HabitMapper.class);
        principalMockito = Mockito.mock(Principal.class);
        Person person1 = new Person(1,"Test User1","user1@server.com","password",0,true);
        Mockito.when(principalMockito.getCurrentPerson()).thenReturn(person1);
        this.mockMvc = MockMvcBuilders.standaloneSetup(new HabitRestController(habitServiceMockito, personServiceMockito, habitMapperMockito, principalMockito)).build();
    }

    @Test
    @DisplayName("Получение списка привычек")
    public void getHabitsTest() throws Exception {
        Person person = new Person(1,"Test User1","user1@server.com","password",0,true);
        Habit habit1 = new Habit(1,"Read book", "read book everyday",person,Period.daily,LocalDate.of(2024, 10,1));
        Habit habit2 = new Habit(2,"yoga", "yoga", person, Period.weekly, LocalDate.of(2024, 10,2));
        Mockito.when(habitServiceMockito.getHabitsByPerson(person)).thenReturn(List.of(habit1,habit2));

        HabitResponse habitResponse1 = new HabitResponse(1,"Read book", "read book everyday",1,1,"2024-10-1");
        HabitResponse habitResponse2 = new HabitResponse(2,"yoga", "yoga",1,2,"2024-10-1");
        when(habitMapperMockito.toListHabitResponse(List.of(habit1,habit2))).thenReturn(List.of(habitResponse1,habitResponse2));

        String result = """
                [
                    {
                        "id": 1,
                        "name": "Read book",
                        "description": "read book everyday",
                        "person_id": 1,
                        "period_id": 1,
                        "registration": "2024-10-1"
                    },
                    {
                        "id": 2,
                        "name": "yoga",
                        "description": "yoga",
                        "person_id": 1,
                        "period_id": 2,
                        "registration": "2024-10-1"
                    }
                ]
                """;

        this.mockMvc.perform(get("/habits")).andDo(print())
                    .andExpect(status().isOk()).andExpect(content().json(result));
    }

    @Test
    @DisplayName("Получение привычки по ID")
    public void getHabitById() throws Exception {
        Person person = new Person(1,"Test User1","user1@server.com","password",0,true);
        Habit habit1 = new Habit(1,"Read book", "read book everyday",person,Period.daily,LocalDate.of(2024, 10,1));
        HabitResponse habitResponse1 = new HabitResponse(1,"Read book", "read book everyday",1,1,"2024-10-1");
        when(habitServiceMockito.getById(1)).thenReturn(habit1);
        when(habitMapperMockito.toHabitResponse(habit1)).thenReturn(habitResponse1);

        String result = """
            {
                "id": 1,
                "name": "Read book",
                "description": "read book everyday",
                "person_id": 1,
                "period_id": 1,
                "registration": "2024-10-1"
            }
                """;

        this.mockMvc.perform(get("/habits/1")).andDo(print())
                    .andExpect(status().isOk()).andExpect(content().json(result));
    }

    @Test
    @DisplayName("Удаление привычки")
    public void deleteHabitTest() throws Exception {
        when(habitServiceMockito.deleteHabit(1)).thenReturn(true);
        this.mockMvc.perform(delete("/habits/1")).andDo(print())
                    .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Добавление привычки")
    public void addHabitTest() throws Exception {
        String jsonHabit = """
            {
                "id": 3,
                "name": "Walk",
                "description": "walk everyday",
                "person_id": 1,
                "period_id": 1,
                "registration": "2024-10-10"
            }
                
                """;

        HabitResponse habitResponse = new HabitResponse(3, "Walk", "walk everyday", 1, 1, "2024-10-10");
        Person person = new Person(1,"Test User1","user1@server.com","password",0,true);
        Habit habit = new Habit(3,"Walk", "walk everyday",person,Period.daily,LocalDate.of(2024, 10,10));

        when(habitMapperMockito.toHabit(habitResponse)).thenReturn(habit);
        when(habitServiceMockito.addHabit(habit)).thenReturn(true);

        this.mockMvc.perform(post("/habits")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonHabit))
                    .andDo(print()).andExpect(status().isOk());
    }

}
