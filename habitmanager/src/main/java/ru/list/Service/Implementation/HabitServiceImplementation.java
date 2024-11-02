package ru.list.Service.Implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import ru.list.Annotation.Audit;
import ru.list.Model.Habit;
import ru.list.Model.LogBook;
import ru.list.Model.Person;
import ru.list.Repository.HabitRepository;
import ru.list.Repository.LogBookRepository;
import ru.list.Service.HabitService;

@Service
public class HabitServiceImplementation implements HabitService {
    private HabitRepository repository = null;
    private LogBookRepository logBookRepository = null;

    public HabitServiceImplementation(HabitRepository repository, LogBookRepository logBookRepository) {
        this.repository = repository;
        this.logBookRepository = logBookRepository;
    }

    @Audit
    @Override
    public boolean addHabit(Habit habit) {
        return repository.save(habit);
    }

    @Audit
    @Override
    public boolean deleteHabit(int id) {

        Habit habit = repository.findById(id);
        List<LogBook> records = logBookRepository.findByHabit(habit);
        for (LogBook logBook : records) {
            if (!logBookRepository.delete(logBook)) {
                return false;
            }
        }
        return repository.delete(habit);
    }

    @Audit
    @Override
    public void editHabit(Habit habit) {
        if (repository.exist(habit)) {
            repository.delete(habit);
        }
        repository.save(habit);
    }

    @Audit
    @Override
    public List<Habit> getHabitsByPerson(Person person) {
        return repository.findByPerson(person);
    }

    @Audit
    @Override
    public boolean deleteHabitByPosition(Person person, int position) {
        List<Habit> habits= repository.findByPerson(person);
        if (position > 0 & position <= habits.size()) {
            return repository.delete(habits.get(position - 1));
        }
        return false;
    }

    @Audit
    @Override
    public Habit getHAbitByPosition(Person person, int position) {
        List<Habit> habits= repository.findByPerson(person);
        if (position > 0 & position <= habits.size()) {
            return habits.get(position - 1);
        }
        return null;
    }

    @Audit
    @Override
    public Habit getById(int id) {
        return repository.findById(id);
    }
}
