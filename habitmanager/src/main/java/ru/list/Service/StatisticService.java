package ru.list.Service;

import java.util.List;
import java.util.Map;

import ru.list.Model.Habit;
import ru.list.Model.LogBook;
import ru.list.Model.Person;

/**
 * сервис по получению статистик
 */
public interface StatisticService {

    /**
     * получение непрерывной последовательности выполнения привычек
     * @param person - пользователь, по которому получается последовательность
     * @return List<LogBook>
     */
    public List<LogBook> streakHabits(Person person);
    /**
     * получение непрерывной последовательности выполнения привычек в виде строк
     * @param person - пользователь, по которому получается последовательность
     * @return List<String>
     */
    public List<String> streakHabitsByString(Person person);
    /**
     * получение процента успешного выполнения привычек
     * @param person  - пользователь, по которому получается последовательность
     * @return - процент
     */
    public double percentSuccess(Person person);
    /**
     * статистика выполнения за период
     * @param person  - пользователь, по которому получается последовательность
     * @param days  - период статистики
     * @return List<LogBook>
     */
    public List<LogBook> executionHabit(Person person, int days);
    /**
     * прогресс выполнения привычки
     * @param person - пользователь
     * @return Map<Habit,Long>
     */
    public Map<Habit,Long> progressHabit(Person person);

}
