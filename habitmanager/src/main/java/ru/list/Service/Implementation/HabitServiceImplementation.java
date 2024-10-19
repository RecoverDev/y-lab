package ru.list.Service.Implementation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ru.list.Model.Habit;
import ru.list.Model.LogBook;
import ru.list.Model.Period;
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


    @Override
    public List<String> getHabitByPersonAsString(Person person) {
        List<Habit> habits = this.getHabitsByPerson(person);
        List<String> strHabits = new ArrayList<>();
        int i = 1;
        for (Habit habit : habits) {
            strHabits.add(String.format("%d. %s - %s", i++, habit.getName(),habit.getPeriod().getDescription()));
        }

        return strHabits;
    }


    @Override
    public boolean deleteHabitByPosition(Person person, int position) {
        List<Habit> habits= repository.findByPerson(person);
        if (position > 0 & position <= habits.size()) {
            return repository.delete(habits.get(position - 1));
        }
        return false;
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
    public boolean addHabitFromString(Person person, List<String> data) {
        if (data.size() == 3) {
            int nomPeriod = Integer.parseInt(data.get(2));
            Habit habit = new Habit(data.get(0), data.get(1), person, Period.values()[nomPeriod - 1], LocalDate.now());
            return this.addHabit(habit);
        }
        return false;
    }

}
