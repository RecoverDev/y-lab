import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import ru.list.Model.Person;
import ru.list.Repository.PersonRepository;
import ru.list.Service.PersonService;
import ru.list.Service.Implementation.PersonServiceImplementation;

public class PersonServiceTest {

    @Mock
    PersonRepository personRepositoryMockito;

    @Test
    @DisplayName("Добавление пользователя")
    public void addPersonTest() {
        Person person = new Person(1,"Test User","user@server.com","password",0,true);
        personRepositoryMockito = Mockito.mock(PersonRepository.class);
        Mockito.when(personRepositoryMockito.save(person)).thenReturn(true);
        Mockito.when(personRepositoryMockito.findByPassword("password")).thenReturn(null);

        PersonService personService = new PersonServiceImplementation(personRepositoryMockito);

        assertThat(personService.addPerson(person)).isTrue();
    }

    @Test
    @DisplayName("Удаление пользователя")
    public void deletePersonTest() {
        Person person = new Person(1,"Test User","user@server.com","password",0,true);
        personRepositoryMockito = Mockito.mock(PersonRepository.class);
        Mockito.when(personRepositoryMockito.delete(person)).thenReturn(true);

        PersonService personService = new PersonServiceImplementation(personRepositoryMockito);

        assertThat(personService.deletePerson(person)).isTrue();
    }

    @Test
    @DisplayName("Получение списка пользователей")
    public void editPersonTest() {
        Person person1 = new Person(1,"Test User1","user1@server.com","password",0,true);
        Person person2 = new Person(2,"Test User2","user2@server.com","password",0,true);
        personRepositoryMockito = Mockito.mock(PersonRepository.class);
        Mockito.when(personRepositoryMockito.findAll()).thenReturn(List.of(person1,person2));

        PersonService personService = new PersonServiceImplementation(personRepositoryMockito);

        assertThat(2).isEqualTo(personService.getPersons().size());
    }

}
