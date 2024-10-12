package ru.list.Controller;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import ru.list.In.StatisticView;
import ru.list.Model.Habit;
import ru.list.Model.LogBook;
import ru.list.Model.Person;
import ru.list.Service.HabitService;
import ru.list.Service.LogBookService;

public class StatisticController {
    private Person currentPerson = null;
    private HabitService habitService = null;
    private LogBookService logBookService = null;
    private StatisticView statisticView = null;

    public StatisticController(Person person, HabitService habitService, LogBookService logBookService) {
        this.currentPerson = person;
        this.habitService = habitService;
        this.logBookService = logBookService;
        statisticView = new StatisticView();
    }

    public void showMenu() {
        int answer = statisticView.showMenu();

        switch (answer) {
            case 1: //текущая серия выполнения привычек
                streakHabits();
                break;
            case 2: //процент успешного выполнения привычек
                percentSuccess();
            case 3: //процент успешного выполнения привычек
                progressHabit();
            default:
                break;
        }
    }

    /**
     * Streak
     */
    private void streakHabits() {
        List<Habit> habits = habitService.getHabitsByPerson(currentPerson);
        List<LogBook> logBooks = logBookService.getLogBookByPerson(currentPerson);
        List<LogBook> result = new ArrayList<>();
        for (Habit habit : habits) {
            List<LogBook> logs = logBooks.stream().filter(l -> l.getHabit().equals(habit)).sorted((l1,l2) -> l1.getDate().compareTo(l2.getDate())).distinct().toList();
            int interval = 1;
            switch (habit.getPeriod()) {
                case daily:
                    interval = 1;
                    break;
                case weekly:
                    interval = 7;
                    break;
                case monthly:
                    interval = 30;
                    break;
                case annually:
                    interval = 365;
                    break;
                default:
                    break;
            }
            for (int i = 1; i < logs.size(); i++) {
                if (Period.between(logs.get(i).getDate(), logs.get(i - 1).getDate()).getDays() == interval) {
                    result.add(logs.get(i - 1));
                }
            }
        }
        List<String> strResult = result.stream()
                                       .sorted((l1,l2) -> l1.getDate().compareTo(l2.getDate()))
                                       .map(l -> String.format("%s - %s", l.getHabit().getName(),l.getDate().toString()))
                                       .toList();
        statisticView.showStatistic(strResult);
    }

    /**
     * процент успешного выполнения привычек
     */
    private void percentSuccess() {
        List<Habit> habits = habitService.getHabitsByPerson(currentPerson);
        List<LogBook> logBooks = logBookService.getLogBookByPerson(currentPerson);
        int expectation = 0;
        int reality = 0;
        for (Habit habit : habits) {
            List<LogBook> logs = logBooks.stream().filter(l -> l.getHabit().equals(habit)).sorted((l1,l2) -> l1.getDate().compareTo(l2.getDate())).distinct().toList();
            int interval = 1;
            switch (habit.getPeriod()) {
                case daily:
                    interval = 1;
                    break;
                case weekly:
                    interval = 7;
                    break;
                case monthly:
                    interval = 30;
                    break;
                case annually:
                    interval = 365;
                    break;
                default:
                    break;
            }
            expectation += Period.between(habit.getRegistration(), LocalDate.now()).getDays() / interval;
            for (LogBook logBook : logs) {
                if(Period.between(logBook.getDate(), LocalDate.now()).getDays() / interval == 0) {
                    reality ++;
                }
            }
        }

        statisticView.showPercent((double)reality/(double)expectation);
    }

    private void progressHabit() {
        List<Habit> habits = habitService.getHabitsByPerson(currentPerson);
        List<LogBook> logBooks = logBookService.getLogBookByPerson(currentPerson);
        List<String> result = new ArrayList<>();

        for (Habit habit : habits) {
            long count = logBooks.stream().filter(l -> l.getHabit().equals(habit)).count();
            result.add(String.format("Привычка \"%s\" выполнена %d раз", habit.getName(), count));
        }
        statisticView.showStatistic(result);

    }

}
