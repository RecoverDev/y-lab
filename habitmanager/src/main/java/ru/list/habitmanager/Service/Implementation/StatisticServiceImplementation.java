package ru.list.habitmanager.Service.Implementation;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.list.habitmanager.Model.Habit;
import ru.list.habitmanager.Model.LogBook;
import ru.list.habitmanager.Repository.HabitRepository;
import ru.list.habitmanager.Repository.LogBookRepository;
import ru.list.habitmanager.Service.StatisticService;

@Service
@RequiredArgsConstructor
public class StatisticServiceImplementation implements StatisticService {
    private final HabitRepository habitRepository;
    private final LogBookRepository logBookRepository;


    @Override
    public List<LogBook> streakHabits(int id) {
        List<Habit> habits = habitRepository.findByPerson(id);
        List<LogBook> logBooks = logBookRepository.findByPerson(id);
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
    public double percentSuccess(int id) {
        List<Habit> habits = habitRepository.findByPerson(id);
        List<LogBook> logBooks = logBookRepository.findByPerson(id);
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
    public List<LogBook> executionHabit(int id, int days) {
        List<LogBook> logs = logBookRepository.findByPerson(id)
                                              .stream()
                                              .filter(l -> Period.between(l.getDate(), LocalDate.now()).getDays() <= days)
                                              .toList();
        return logs;

    }

    @Override
    public Map<Habit, Long> progressHabit(int id) {
        List<Habit> habits = habitRepository.findByPerson(id);
        List<LogBook> logBooks = logBookRepository.findByPerson(id);
        Map<Habit,Long> result = new HashMap<>();

        for (Habit habit : habits) {
            long count = logBooks.stream().filter(l -> l.getHabit().equals(habit)).count();
            result.put(habit, count);
        }
        return result;
    }

}
