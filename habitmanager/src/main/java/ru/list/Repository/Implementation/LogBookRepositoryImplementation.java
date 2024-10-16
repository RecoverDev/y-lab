package ru.list.Repository.Implementation;

import java.util.ArrayList;
import java.util.List;

import ru.list.Model.Habit;
import ru.list.Model.LogBook;
import ru.list.Model.Person;
import ru.list.Repository.LogBookRepository;

public class LogBookRepositoryImplementation implements LogBookRepository{
    private List<LogBook> repository = new ArrayList<>();

    @Override
    public boolean save(LogBook logBook) {
        return repository.add(logBook);
    }

    @Override
    public boolean delete(LogBook logBook) {
        return repository.remove(logBook);
    }

    @Override
    public List<LogBook> findByPerson(Person person) {
        return repository.stream().filter(l -> l.getHabit().getPerson().equals(person)).toList();
    }

    @Override
    public List<LogBook> findAll() {
        return repository;
    }

    @Override
    public List<LogBook> findByHabit(Habit habit) {
        return repository.stream().filter(l -> l.getHabit().equals(habit)).toList();
    }

}
