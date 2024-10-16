package ru.list.Service;

import java.util.List;

import ru.list.Model.Habit;
import ru.list.Model.LogBook;
import ru.list.Model.Person;

/**
 * Сервис по управлению журналом с записями по выполнению привычек
 */
public interface LogBookService {

    /**
     * Добавляет новую запись в журнал
     * @param logBook - новая запись
     * @return - результат операции (true - успех/false - неуспех)
     */
    boolean addLogBook(LogBook logBook);
    /**
     * Удаляет запись из журнала
     * @param logBook - удаляемая запись
     * @return - результат операции (true - успех/false - неуспех)
     */
    boolean deleteLogBook(LogBook logBook);
    /**
     * Получает все записи по выбранной привычке
     * @param habit - выбранная привычка
     * @return List<LogBook>
     */
    List<LogBook> getLogBookByHabit(Habit habit);
    /**
     * Получает все записи пользователя
     * @param person - пользователь
     * @return List<LogBook>
     */
    List<LogBook> getLogBookByPerson(Person person);

}
