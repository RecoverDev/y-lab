package ru.list.Repository;

import java.util.List;

import ru.list.Model.Habit;
import ru.list.Model.LogBook;
import ru.list.Model.Person;

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
    boolean delete(LogBook logBook);
    /**
     * возвращает список записей о привычках пользователя
     * @param person - пользователь
     * @return List<LogBook>
     */
    List<LogBook> findByPerson(Person person);
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

}
