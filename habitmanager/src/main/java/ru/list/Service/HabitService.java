package ru.list.Service;

import java.util.List;

import ru.list.Model.Habit;
import ru.list.Model.Person;

/**
 * сервис управления привычками
 */
public interface HabitService {

    /**
     * Добавление новой привычки
     * @param habit - новая привычка
     * @return - результат операции (true - успех/false - неуспех)
     */
    boolean addHabit(Habit habit);
    /**
     * Удаление привычки
     * @param habit - удаляемая привычка
     * @return - результат операции (true - успех/false - неуспех)
     */
    boolean deleteHabit(Habit habit);
    /**
     * Редактирование привычки
     * @param habit - редактируемая привычка
     */
    void editHabit(Habit habit);
    /**
     * Получение списка привычек пользователя
     * @param person - пользователь
     * @return List<Habit>
     */
    List<Habit> getHabitsByPerson(Person person);

}
