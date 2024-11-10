package ru.list.habitmanager.Service.Implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.list.habitmanager.Model.Habit;
import ru.list.habitmanager.Model.LogBook;
import ru.list.habitmanager.Repository.HabitRepository;
import ru.list.habitmanager.Repository.LogBookRepository;
import ru.list.habitmanager.Service.LogBookService;

@Service
@RequiredArgsConstructor
public class LogBookServiceImplementation implements LogBookService{
    private final LogBookRepository repository;
    private final HabitRepository habitRepository;

    @Override
    public List<LogBook> getLogBooks() {
        return (List<LogBook>)repository.findAll();
    }

    @Override
    public LogBook getLogBookById(int id) {
        return repository.findById(id);
    }


    @Override
    public boolean addLogBook(LogBook logBook) {
        return repository.save(logBook);
    }

    @Override
    public boolean deleteLogBook(LogBook logBook) {
        return repository.delete(logBook.getId());
    }

    @Override
    public boolean deleteLogBookById(int id) {
        return repository.delete(id);
    }

    @Override
    public List<LogBook> getLogBookByHabit(int id) {
        Habit result = habitRepository.findById(id);
        if (result != null) {
            return repository.findByHabit(result);
        }
        return null;
    }

    @Override
    public List<LogBook> getLogBookByPerson(int id) {
        List<LogBook> result = (List<LogBook>)repository.findAll();
        return result.stream().filter(l -> l.getHabit().getPerson().getId() == id).toList();
    }

}
