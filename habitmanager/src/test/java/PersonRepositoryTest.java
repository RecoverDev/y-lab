import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ru.list.Model.Person;
import ru.list.Repository.PersonRepository;
import ru.list.Repository.Implementation.PersonRepositoryImplementation;

public class PersonRepositoryTest {

    @Test
    @DisplayName("Добавление нового пользователя")
    public void PersonRepositoryAddPerson() {
        PersonRepository repository = new PersonRepositoryImplementation();
        Person person = new Person(1,"FirstPerson","email@server.ru","password",0,true);
        
        assertThat(repository.save(person)).isTrue();
        assertThat(1).isEqualTo(repository.findAll().size());
    }

    @Test
    @DisplayName("Попытка добавить двух пользователей с одинаковыми E-Mail-ом и паролем")
    public void PersonRepositoryAddDublicate() {
        PersonRepository repository = new PersonRepositoryImplementation();
        Person person1 = new Person(1,"FirstPerson","email@server.ru","password",0,true);
        Person person2 = new Person(2,"SecondPerson","email@server.ru","password",1,true);

        repository.save(person1);
        repository.save(person2);

        assertThat(1).isEqualTo(repository.findAll().size());
        assertThat(person1).isEqualTo(repository.findByEmailAndPassword(person1.getEmail(), person1.getPassword()));
    }

    @Test
    @DisplayName("Попытка получить несуществующего пользователя (ожидаем null)")
    public void PersonRepositoryGetNull() {
        PersonRepository repository = new PersonRepositoryImplementation();
        Person person1 = new Person(1,"FirstPerson","email@server.ru","password",0,true);
        repository.save(person1);

        assertThat(repository.findByEmailAndPassword("fantom@server.ru", "empty")).isNull();
    }

    @Test
    @DisplayName("Удаление пользователя")
    public void PersonRepositoryDeletePerson() {
        PersonRepository repository = new PersonRepositoryImplementation();
        Person person1 = new Person(1,"FirstPerson","email@server.ru","password",0,true);
        Person person2 = new Person(2,"SecondPerson","second@server.ru","word",1,true);

        repository.save(person1);
        repository.save(person2);

        assertThat(repository.delete(person1)).isTrue();
        assertThat(1).isEqualTo(repository.findAll().size());
        assertThat(person2).isEqualTo(repository.findByEmailAndPassword(person2.getEmail(), person2.getPassword()));
    }

    @Test
    @DisplayName("Проверка наличия пользователя в хранилище")
    public void PersonRepositoryExistPerson() {
        PersonRepository repository = new PersonRepositoryImplementation();
        Person person1 = new Person(1,"FirstPerson","email@server.ru","password",0,true);
        Person person2 = new Person(2,"SecondPerson","second@server.ru","word",1,true);
        Person person3 = new Person(3,"ThirdPerson","third@server.ru","key",0,true);

        repository.save(person1);
        repository.save(person2);

        assertThat(repository.exist(person2)).isTrue();
        assertThat(repository.exist(person3)).isFalse();
    }

    @Test
    @DisplayName("Получение пользователя по паролю")
    public void PersonRepositoryFindByPassword() {
        PersonRepository repository = new PersonRepositoryImplementation();
        Person person1 = new Person(1,"FirstPerson","email@server.ru","password",0,true);
        Person person2 = new Person(2,"SecondPerson","second@server.ru","word",1,true);

        repository.save(person1);
        repository.save(person2);

        assertThat(person1).isEqualTo(repository.findByPassword("password"));
        assertThat(repository.findByPassword("key")).isNull();
    }

}
