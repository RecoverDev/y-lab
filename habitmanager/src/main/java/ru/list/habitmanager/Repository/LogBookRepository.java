package ru.list.habitmanager.Repository;

import java.util.List;

import ru.list.habitmanager.Model.Habit;
import ru.list.habitmanager.Model.LogBook;

/**
 * хранилище записей о выполнении привычки
 */
public interface LogBookRepository {

    /**
     * добавление новой записи о выполнении привычки
     * @param logBook - новая запись
     * @return - результат операции (true - успех/false - неуспех)
     */
    boolean save(LogBook logBook);
    /**
     * удаление записи о выполнении привычки
     * @param logBook - удаляемая запись
     * @return - результат операции (true - успех/false - неуспех)
     */
    boolean delete(int id);
    /**
     * возвращает список записей о привычках пользователя
     * @param id - ID пользователя
     * @return List<LogBook>
     */
    List<LogBook> findByPerson(int id);
    /**
     * возвращает список всех привычек в хранилище
     * @return List<LogBook>
     */
    List<LogBook> findAll();
    /**
     * возвращает список записей о привычке
     * @param habit - привычка
     * @return List<LogBook>
     */
    List<LogBook> findByHabit(Habit habit);
    /**
     * возвращает запись по ID
     * @param id - идентификатор
     * @return - LogBook
     */
    LogBook findById(int id);

}
