package ru.list.Service.Implementation;

import java.util.List;

import ru.list.Model.Habit;
import ru.list.Model.LogBook;
import ru.list.Model.Person;
import ru.list.Repository.LogBookRepository;
import ru.list.Service.LogBookService;

public class LogBookServiceImplementation implements LogBookService {
    private LogBookRepository repository = null;

    public LogBookServiceImplementation(LogBookRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean addLogBook(LogBook logBook) {
        return repository.save(logBook);
    }

    @Override
    public boolean deleteLogBook(LogBook logBook) {
        return repository.delete(logBook);
    }

    @Override
    public List<LogBook> getLogBookByHabit(Habit habit) {
        return repository.findByHabit(habit);
    }

    @Override
    public List<LogBook> getLogBookByPerson(Person person) {
        return repository.findByPerson(person);
    }

}
