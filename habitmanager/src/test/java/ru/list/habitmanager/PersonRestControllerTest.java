package ru.list.habitmanager;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ru.list.habitmanager.Controller.PersonRestController;
import ru.list.habitmanager.DTO.PersonResponse;
import ru.list.habitmanager.Mapper.PersonMapper;
import ru.list.habitmanager.Model.Person;
import ru.list.habitmanager.Model.Role;
import ru.list.habitmanager.Service.PersonService;

import org.springframework.http.MediaType;


public class PersonRestControllerTest {
    MockMvc mockMvc;

    @Mock
    PersonService personServiceMockito;

    @Mock
    PersonMapper personMapperMockito;

    @BeforeEach
    public void setup() {
        personServiceMockito = Mockito.mock(PersonService.class);
        personMapperMockito = Mockito.mock(PersonMapper.class);
        this.mockMvc = MockMvcBuilders.standaloneSetup(new PersonRestController(personServiceMockito, personMapperMockito)).build();
    }


    @Test
    @DisplayName("Получение списка пользователей")
    public void getPersonsTest() throws Exception {

        Person person1 = new Person(1,"Test User1","user1@server.com","password", Role.ROLE_USER,true);
        Person person2 = new Person(2,"Test User2","user2@server.com","password", Role.ROLE_USER,true);

        PersonResponse personResponse1 = new PersonResponse(1,"Test User1","user1@server.com", "ROLE_USER",true);
        PersonResponse personResponse2 = new PersonResponse(2,"Test User2","user2@server.com", "ROLE_USER",true);

        when(personServiceMockito.getPersons()).thenReturn(List.of(person1,person2));
        when(personMapperMockito.toListPersonResponse(List.of(person1,person2))).thenReturn(List.of(personResponse1,personResponse2));

        String result = """
                [
                    {
                        "id": 1,
                        "username": "Test User1",
                        "email": "user1@server.com",
                        "role": "ROLE_USER",
                        "blocked": true
                    },
                    {
                        "id": 2,
                        "username": "Test User2",
                        "email": "user2@server.com",
                        "role": "ROLE_USER",
                        "blocked": true
                    }
                ]
                """;

                this.mockMvc.perform(get("/persons")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(result));

    }

    @Test
    @DisplayName("Получение пользователя по ID")
    public void getPersonByIdTest() throws Exception {
        Person person1 = new Person(1,"Test User1","user1@server.com","password", Role.ROLE_USER,true);

        PersonResponse personResponse1 = new PersonResponse(1,"Test User1","user1@server.com", "ROLE_USER",true);

        when(personServiceMockito.getPersonById(1)).thenReturn(person1);
        when(personMapperMockito.toPersonResponse(person1)).thenReturn(personResponse1);

        String result = """
                {
                    "id": 1,
                    "username": "Test User1",
                    "email": "user1@server.com",
                    "role": "ROLE_USER",
                    "blocked": true
                }
                """;

                this.mockMvc.perform(get("/persons/1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(result));
    }

    @Test
    @DisplayName("Удаление пользователя")
    public void deletePersonByIDTest() throws Exception {
        when(personServiceMockito.deletePerson(1)).thenReturn(true);

        this.mockMvc.perform(delete("/persons/1")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Добавление пользователя")
    public void addPersonTest() throws Exception {
        Person person3 = new Person(3,"Test User3","user3@server.com","password", Role.ROLE_USER,true);

        PersonResponse personResponse3 = new PersonResponse(3,"Test User3","user3@server.com", "ROLE_USER",true);

        when(personMapperMockito.toPerson(personResponse3)).thenReturn(person3);
        when(personServiceMockito.addPerson(person3)).thenReturn(true);

        String jsonPersonResponse3 = """
                {
                    "id": 3,
                    "username": "Test User3",
                    "email": "user3@server.com",
                    "role": "ROLE_USER",
                    "blocked": true
                }
                """;

        this.mockMvc.perform(post("/persons")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonPersonResponse3))
                    .andDo(print()).andExpect(status().isOk());
    }

}
