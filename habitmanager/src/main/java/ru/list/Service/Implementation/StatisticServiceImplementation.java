package ru.list.Service.Implementation;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.list.Model.Habit;
import ru.list.Model.LogBook;
import ru.list.Model.Person;
import ru.list.Repository.HabitRepository;
import ru.list.Repository.LogBookRepository;
import ru.list.Service.StatisticService;

public class StatisticServiceImplementation implements StatisticService {
    private HabitRepository habitRepository = null;
    private LogBookRepository logBookRepository = null;

    public StatisticServiceImplementation(HabitRepository habitRepository, LogBookRepository logBookRepository) {
        this.habitRepository = habitRepository;
        this.logBookRepository = logBookRepository;
    }

    @Override
    public List<LogBook> streakHabits(Person person) {
        List<Habit> habits = habitRepository.findByPerson(person);
        List<LogBook> logBooks = logBookRepository.findByPerson(person);
        List<LogBook> result = new ArrayList<>();
        for (Habit habit : habits) {
            List<LogBook> logs = logBooks.stream().filter(l -> l.getHabit().equals(habit)).sorted((l1,l2) -> (l1.getDate().compareTo(l2.getDate()) * -1)).toList();

            if (logs.size() > 0) {
                int interval = habit.getPeriod().getInterval();
                result.add(logs.get(0));

                for (int i = 1; i < logs.size(); i++) {
                    if (Period.between(logs.get(i).getDate(), logs.get(i - 1).getDate()).getDays() == interval) {
                        result.add(logs.get(i - 1));
                    } else {
                        break;
                    }
                }
            }
        }

        return result;
    }

    @Override
    public double percentSuccess(Person person) {
        List<Habit> habits = habitRepository.findByPerson(person);
        List<LogBook> logBooks = logBookRepository.findByPerson(person);
        double expectation = 0;
        double reality = 0;
        for (Habit habit : habits) {
            int interval = habit.getPeriod().getInterval();

            expectation += Period.between(habit.getRegistration(), LocalDate.now()).getDays() / interval;
            reality += logBooks.stream().filter(l -> l.getHabit().equals(habit)).count();
        }
        if (expectation == 0) {
            expectation = 1;
        }
        return reality/expectation * 100;
    }

    @Override
    public List<LogBook> executionHabit(Person person, int days) {
        List<LogBook> logs = logBookRepository.findByPerson(person)
                                              .stream()
                                              .filter(l -> Period.between(l.getDate(), LocalDate.now()).getDays() <= days)
                                              .toList();
        return logs;

    }

    @Override
    public Map<Habit, Long> progressHabit(Person person) {
        List<Habit> habits = habitRepository.findByPerson(person);
        List<LogBook> logBooks = logBookRepository.findByPerson(person);
        Map<Habit,Long> result = new HashMap<>();

        for (Habit habit : habits) {
            long count = logBooks.stream().filter(l -> l.getHabit().equals(habit)).count();
            result.put(habit, count);
        }
        return result;
    }

    @Override
    public List<String> streakHabitsByString(Person person) {
        List<LogBook> result = this.streakHabits(person);
        return result.stream()
                     .sorted((l1,l2) -> l1.getDate().compareTo(l2.getDate()))
                     .map(l -> String.format("%s - %s", l.getHabit().getName(),l.getDate().toString()))
                     .toList();
    }

}
