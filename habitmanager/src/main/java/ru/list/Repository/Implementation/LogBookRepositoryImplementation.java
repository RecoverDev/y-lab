package ru.list.Repository.Implementation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ru.list.Model.Habit;
import ru.list.Model.LogBook;
import ru.list.Model.Person;
import ru.list.Repository.LogBookRepository;

public class LogBookRepositoryImplementation implements LogBookRepository{
    private Set<LogBook> repository = new HashSet<>();

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
        return repository.stream().toList();
    }

    @Override
    public List<LogBook> findByHabit(Habit habit) {
        return repository.stream().filter(l -> l.getHabit().equals(habit)).toList();
    }

}
