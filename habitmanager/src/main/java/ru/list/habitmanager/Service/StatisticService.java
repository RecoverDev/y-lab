package ru.list.habitmanager.Service;

import java.util.List;
import java.util.Map;

import ru.list.habitmanager.Model.Habit;
import ru.list.habitmanager.Model.LogBook;
import ru.list.habitmanager.Model.Person;


/**
 * сервис по получению статистик
 */
public interface StatisticService {

    /**
     * получение непрерывной последовательности выполнения привычек
     * @param person - пользователь, по которому получается последовательность
     * @return List<LogBook>
     */
    public List<LogBook> streakHabits(int id);
    /**
     * получение процента успешного выполнения привычек
     * @param id  - ID пользователя, по которому получается последовательность
     * @return - процент
     */
    public double percentSuccess(int id);
    /**
     * статистика выполнения за период
     * @param id  - ID пользователя, по которому получается последовательность
     * @param days  - период статистики
     * @return List<LogBook>
     */
    public List<LogBook> executionHabit(int id, int days);
    /**
     * прогресс выполнения привычки
     * @param id - ID пользователя
     * @return Map<Habit,Long>
     */
    public Map<Habit,Long> progressHabit(int id);

}
