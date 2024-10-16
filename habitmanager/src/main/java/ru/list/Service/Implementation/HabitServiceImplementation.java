package ru.list.Service.Implementation;

import java.util.List;

import ru.list.Model.Habit;
import ru.list.Model.LogBook;
import ru.list.Model.Person;
import ru.list.Repository.HabitRepository;
import ru.list.Repository.LogBookRepository;
import ru.list.Service.HabitService;

public class HabitServiceImplementation implements HabitService {
    private HabitRepository repository = null;
    private LogBookRepository logBookRepository = null;

    public HabitServiceImplementation(HabitRepository repository, LogBookRepository logBookRepository) {
        this.repository = repository;
        this.logBookRepository = logBookRepository;
    }


    @Override
    public boolean addHabit(Habit habit) {
        return repository.save(habit);
    }

    @Override
    public boolean deleteHabit(Habit habit) {

        List<LogBook> records = logBookRepository.findByHabit(habit);
        for (LogBook logBook : records) {
            if (!logBookRepository.delete(logBook)) {
                return false;
            }
        }
        return repository.delete(habit);
    }

    @Override
    public void editHabit(Habit habit) {
        if (repository.exist(habit)) {
            repository.delete(habit);
        }
        repository.save(habit);
    }


    @Override
    public List<Habit> getHabitsByPerson(Person person) {
        return repository.findByPerson(person);
    }

}
