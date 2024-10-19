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
     * Добавление новой привычки по строковым данным
     * @param person - пользователь, которому принадлежит привычка
     * @param List<String> - данные
     * @return - результат операции (true - успех/false - неуспех)
     */
    boolean addHabitFromString(Person person, List<String> data);
    /**
     * Удаление привычки
     * @param habit - удаляемая привычка
     * @return - результат операции (true - успех/false - неуспех)
     */
    boolean deleteHabit(Habit habit);
    /**
     * Удаление привычки по номеру позиции
     * @param person - пользователь
     * @param position - номер позиции
     * @return - результат операции (true - успех/false - неуспех)
     */
    boolean deleteHabitByPosition(Person person, int position);
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
    /**
     * Получение списка привычек пользователя в виде списка строк
     * @param person - пользователь
     * @return List<String>
     */
    List<String> getHabitByPersonAsString(Person person);
    /**
     * Получение привычки по номеру
     * @param person - пользователь
     * @param position - номер позиции
     * @return - привычка
     */
    Habit getHAbitByPosition(Person person, int position);

}
