package ru.list.habitmanager.Service;

import java.util.List;

import ru.list.habitmanager.Model.LogBook;


/**
 * Сервис по управлению журналом с записями по выполнению привычек
 */
public interface LogBookService {

    /**
     * Получает полный список записей в журнале
     * @return - List<LogBook>
     */
    List<LogBook> getLogBooks();
    /**
     * Получает запись в журнале по ID
     * @param id - идентификатор записи
     * @return - LogBook
     */
    LogBook getLogBookById(int id);
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
     * Удаляет запись из журнала по ID
     * @param id - идентификатор записи
     * @return - результат операции (true - успех/false - неуспех)
     */
    boolean deleteLogBookById(int id);
    /**
     * Получает все записи по выбранной привычке
     * @param habit - ID выбранной привычки
     * @return List<LogBook>
     */
    List<LogBook> getLogBookByHabit(int id);
    /**
     * Получает все записи пользователя
     * @param person - ID пользователя
     * @return List<LogBook>
     */
    List<LogBook> getLogBookByPerson(int id);

}
