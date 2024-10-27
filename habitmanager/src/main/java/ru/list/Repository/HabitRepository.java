package ru.list.Repository;

import java.util.List;

import ru.list.Model.Habit;
import ru.list.Model.Person;

/**
 * хранилище привычек пользователей
 */
public interface HabitRepository {

    /**
     * добавление новой привычки в хранилище
     * @param habit - привычка
     * @return - результат операции (true - успех/false - неуспех)
     */
    boolean save(Habit habit);
    /**
     * удаление привычки из хранилища
     * @param habit - привычка
     * @return - результат операции (true - успех/false - неуспех)
     */
    boolean delete(Habit habit);
    /**
     * возвращает список всех привычек пользователя
     * @param person - пользователь
     * @return - List<Yabit>
     */
    List<Habit> findByPerson(Person person);
    /**
     * возвращает список всех привычек в хранилище
     * @return - List<Habit>
     */
    List<Habit> findAll();
    /**
     * возвращает привычку по иднтификатору
     * @param id - идентификатор
     * @return - Habit
     */
    Habit findById(int id);
    /**
     * сообщает есть ли такая привычка в хранилище
     * @param habit - привычка
     * @return - результат операции (true - успех/false - неуспех)
     */
    boolean exist(Habit habit);
}
