package ru.list.Repository.Implementation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ru.list.Model.Habit;
import ru.list.Model.Person;
import ru.list.Repository.HabitRepository;

public class HabitRepositoryImplementation implements HabitRepository {
    private Set<Habit> repository = new HashSet<>();

    @Override
    public boolean save(Habit habit) {
        return repository.add(habit);
    }

    @Override
    public boolean delete(Habit habit) {
        return repository.remove(habit);
    }

    @Override
    public List<Habit> findByPerson(Person person) {
        return repository.stream().filter(h -> h.getPerson().equals(person)).toList();
    }

    @Override
    public List<Habit> findAll() {
        return repository.stream().toList();
    }

    @Override
    public boolean exist(Habit habit) {
        return repository.contains(habit);
    }

}
