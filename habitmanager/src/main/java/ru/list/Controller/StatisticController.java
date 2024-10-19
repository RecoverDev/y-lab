package ru.list.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ru.list.Model.Habit;
import ru.list.Model.Person;
import ru.list.Out.StatisticView;
import ru.list.Service.StatisticService;

public class StatisticController {
    private Person currentPerson = null;

    private StatisticService statisticService = null;
    private StatisticView statisticView = null;

    public void setCurrentPerson(Person currentPerson) {
        this.currentPerson = currentPerson;
    }

    public StatisticController(Person person, StatisticService statisticService) {
        this.currentPerson = person;
        this.statisticService = statisticService;
        statisticView = new StatisticView();
    }

    public void showMenu() {
        int answer = statisticView.showMenu();

        switch (answer) {
            case 1 -> streakHabits();
            case 2 -> percentSuccess();
            case 3 -> progressHabit();
            case 4 -> executionHabit();
        }
    }

    /**
     * Streak
     */
    private void streakHabits() {
        statisticView.showStatistic(statisticService.streakHabitsByString(currentPerson));
    }

    /**
     * процент успешного выполнения привычек
     */
    private void percentSuccess() {
        double result = statisticService.percentSuccess(currentPerson);
        statisticView.showPercent(result);
    }

    /**
     * прогресс выполнения привычки
     */
    private void progressHabit() {
        Map<Habit,Long> progress = statisticService.progressHabit(currentPerson);
        List<String> result = new ArrayList<>();

        for (Map.Entry<Habit, Long> entry : progress.entrySet()) {
            result.add(String.format("Привычка \"%s\" выполнена %d раз", entry.getKey().getName(), entry.getValue()));
        }

        statisticView.showStatistic(result);

    }

    /**
     * статистика выполнения
     */
    private void executionHabit() {
        List<String> executionPeriod = List.of("1. День", "2. Неделя", "3. Месяц", "4. Год");

        int answer = statisticView.choicePeriod(executionPeriod);

        List<String> logs = statisticService.executionHabit(currentPerson, ru.list.Model.Period.values()[answer - 1].getInterval())
                                            .stream()
                                            .map(l -> String.format("%s - %s",l.getHabit().getName(),l.getDate().toString()))
                                            .toList(); ;
        statisticView.showStatistic(logs);
    }

}
