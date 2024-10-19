
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import ru.list.Model.Person;
import ru.list.Repository.PersonRepository;
import ru.list.Service.AutorizationService;
import ru.list.Service.Implementation.AutorizationServiceImplementation;

public class AutorizationServiceTest {
    
    @Mock
    PersonRepository personRepositoryMockito;


    @Test
    @DisplayName("Авторизация пользователя")
    public void autorizateTest() {
        personRepositoryMockito = Mockito.mock(PersonRepository.class);
        Mockito.when(personRepositoryMockito.findByEmailAndPassword("user@server.com","password")).thenReturn(new Person(1,"Test User","user@server.com","password",0,true));

        AutorizationService autorizationService = new AutorizationServiceImplementation(personRepositoryMockito);

        Person person = autorizationService.autorizate("user@server.com", "password");

        assertThat("Test User").isEqualTo(person.getName());
    }

}
