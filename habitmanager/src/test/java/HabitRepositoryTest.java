import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ru.list.Model.Habit;
import ru.list.Model.Period;
import ru.list.Model.Person;
import ru.list.Repository.HabitRepository;
import ru.list.Repository.Implementation.HabitRepositoryImplementation;

public class HabitRepositoryTest {

    @Test
    @DisplayName("Добавление новой привычки")
    public void HabitRepositoryAddHabit() {
        HabitRepository repository = new HabitRepositoryImplementation();
        Person person = new Person("FirstPerson","email@server.ru","password",0,true);
        Habit habit = new Habit("Полезная привычка","Очень полезная привычка", person, Period.daily,LocalDate.now());

        assertThat(repository.save(habit)).isTrue();
        assertThat(repository.findAll().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Удаление привычки")
    public void HabitRepositoryDeleteHabit() {
        HabitRepository repository = new HabitRepositoryImplementation();
        Person person = new Person("FirstPerson","email@server.ru","password",0,true);
        Habit habit1 = new Habit("Полезная привычка","Очень полезная привычка", person, Period.daily,LocalDate.now());
        Habit habit2 = new Habit("Вторая полезная привычка","Очень полезная привычка", person, Period.daily,LocalDate.now());

        repository.save(habit1);
        repository.save(habit2);

        assertThat(repository.delete(habit1)).isTrue();
        assertThat(repository.findAll().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Получение списка привычек одного пользователя")
    public void HabitRepositoryFindPersonHabits() {
        HabitRepository repository = new HabitRepositoryImplementation();

        Person person1 = new Person("FirstPerson","email@server.ru","password",0,true);
        Habit habit1 = new Habit("Полезная привычка","Очень полезная привычка", person1, Period.daily,LocalDate.now());
        Habit habit2 = new Habit("Вторая полезная привычка","Очень полезная привычка", person1, Period.daily,LocalDate.now());

        Person person2 = new Person("SecondPerson","box@server.ru","word",0,true);
        Habit habit3 = new Habit("Полезная привычка","Очень полезная привычка", person2, Period.daily,LocalDate.now());
        Habit habit4 = new Habit("Вторая полезная привычка","Очень полезная привычка", person2, Period.daily,LocalDate.now());

        repository.save(habit1);
        repository.save(habit2);
        repository.save(habit3);
        repository.save(habit4);

        List<Habit> habits = repository.findByPerson(person1);

        assertThat(2).isEqualTo(habits.size());
    }

    @Test
    @DisplayName("Получение списка всех привычек")
    public void HabitRepositoryFindAll() {
        HabitRepository repository = new HabitRepositoryImplementation();

        Person person1 = new Person("FirstPerson","email@server.ru","password",0,true);
        Habit habit1 = new Habit("Полезная привычка","Очень полезная привычка", person1, Period.daily,LocalDate.now());
        Habit habit2 = new Habit("Вторая полезная привычка","Очень полезная привычка", person1, Period.daily,LocalDate.now());

        Person person2 = new Person("SecondPerson","box@server.ru","word",0,true);
        Habit habit3 = new Habit("Полезная привычка","Очень полезная привычка", person2, Period.daily,LocalDate.now());
        Habit habit4 = new Habit("Вторая полезная привычка","Очень полезная привычка", person2, Period.daily,LocalDate.now());

        repository.save(habit1);
        repository.save(habit2);
        repository.save(habit3);
        repository.save(habit4);

        assertThat(4).isEqualTo(repository.findAll().size());
    }

}
