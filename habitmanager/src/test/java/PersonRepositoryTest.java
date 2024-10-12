import org.junit.jupiter.api.Assertions;
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
        Person person = new Person("FirstPerson","email@server.ru","password",0);
        
        Assertions.assertTrue(repository.save(person));
        Assertions.assertEquals(1, repository.findAll().size());
    }

    @Test
    @DisplayName("Попытка добавить двух пользователей с одинаковыми E-Mail-ом и паролем")
    public void PersonRepositoryAddDublicate() {
        PersonRepository repository = new PersonRepositoryImplementation();
        Person person1 = new Person("FirstPerson","email@server.ru","password",0);
        Person person2 = new Person("SecondPerson","email@server.ru","password",1);

        repository.save(person1);
        repository.save(person2);

        Assertions.assertEquals(1, repository.findAll().size());
        Assertions.assertEquals(person1, repository.findByEmailAndPassword(person1.getEmail(), person1.getPassword()));
    }

    @Test
    @DisplayName("Попытка получить несуществующего пользователя (ожидаем null)")
    public void PersonRepositoryGetNull() {
        PersonRepository repository = new PersonRepositoryImplementation();
        Person person1 = new Person("FirstPerson","email@server.ru","password",0);
        repository.save(person1);

        Assertions.assertNull(repository.findByEmailAndPassword("fantom@server.ru", "empty"));
    }

    @Test
    @DisplayName("Удаление пользователя")
    public void PersonRepositoryDeletePerson() {
        PersonRepository repository = new PersonRepositoryImplementation();
        Person person1 = new Person("FirstPerson","email@server.ru","password",0);
        Person person2 = new Person("SecondPerson","second@server.ru","word",1);

        repository.save(person1);
        repository.save(person2);

        Assertions.assertTrue(repository.delete(person1));
        Assertions.assertEquals(1, repository.findAll().size());
        Assertions.assertEquals(person2, repository.findByEmailAndPassword(person2.getEmail(), person2.getPassword()));
    }

    @Test
    @DisplayName("Проверка наличия пользователя в хранилище")
    public void PersonRepositoryExistPerson() {
        PersonRepository repository = new PersonRepositoryImplementation();
        Person person1 = new Person("FirstPerson","email@server.ru","password",0);
        Person person2 = new Person("SecondPerson","second@server.ru","word",1);
        Person person3 = new Person("ThirdPerson","third@server.ru","key",0);

        repository.save(person1);
        repository.save(person2);

        Assertions.assertTrue(repository.exist(person2));
        Assertions.assertFalse(repository.exist(person3));

    }

    @Test
    @DisplayName("Получение пользователя по паролю")
    public void PersonRepositoryFindByPassword() {
        PersonRepository repository = new PersonRepositoryImplementation();
        Person person1 = new Person("FirstPerson","email@server.ru","password",0);
        Person person2 = new Person("SecondPerson","second@server.ru","word",1);

        repository.save(person1);
        repository.save(person2);

        Assertions.assertEquals(person1, repository.findByPassword("password"));
        Assertions.assertNull(repository.findByPassword("key"));
    }

}
