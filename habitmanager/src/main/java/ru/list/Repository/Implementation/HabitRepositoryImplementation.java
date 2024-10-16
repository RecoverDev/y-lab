package ru.list.Repository.Implementation;

import java.util.ArrayList;
import java.util.List;

import ru.list.Model.Habit;
import ru.list.Model.Person;
import ru.list.Repository.HabitRepository;

public class HabitRepositoryImplementation implements HabitRepository {
    private List<Habit> repository = new ArrayList<>();

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
        return repository;
    }

    @Override
    public boolean exist(Habit habit) {
        return repository.contains(habit);
    }

}
