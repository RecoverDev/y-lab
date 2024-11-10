package ru.list.habitmanager.Service.Implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.list.habitmanager.Model.Habit;
import ru.list.habitmanager.Model.LogBook;
import ru.list.habitmanager.Model.Person;
import ru.list.habitmanager.Repository.HabitRepository;
import ru.list.habitmanager.Repository.LogBookRepository;
import ru.list.habitmanager.Service.HabitService;

@Service
@RequiredArgsConstructor
public class HabitServiceImplementation implements HabitService {
    private final HabitRepository repository = null;
    private final LogBookRepository logBookRepository = null;

    @Override
    public boolean addHabit(Habit habit) {
        return repository.save(habit);
    }

    @Override
    public boolean deleteHabit(int id) {

        Habit habit = repository.findById(id);
        if (habit == null) {
            return false;
        }
        List<LogBook> logBooks = logBookRepository.findByHabit(habit);
        for (LogBook logBook : logBooks) {
            if (logBookRepository.delete(logBook.getId())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void editHabit(Habit habit) {
        repository.save(habit);
    }

    @Override
    public List<Habit> getHabitsByPerson(Person person) {
        return repository.findByPerson(person);
    }

    @Override
    public Habit getHAbitByPosition(Person person, int position) {
        List<Habit> habits= repository.findByPerson(person);
        if (position > 0 & position <= habits.size()) {
            return habits.get(position - 1);
        }
        return null;
    }

    @Override
    public Habit getById(int id) {
        return repository.findById(id);
    }
}
