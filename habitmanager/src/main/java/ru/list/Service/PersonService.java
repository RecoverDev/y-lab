package ru.list.Service;

import java.util.List;

import ru.list.Model.Person;

/**
 * Сервис по управлению пользователями
 */
public interface PersonService {

    /**
     * Добавление нового пользователя
     * @param person - новый пользователь
     * @return - результат операции (true - успех/false - неуспех)
     */
    boolean addPerson(Person person);
    /**
     * Удаление пользователя
     * @param person - удаляемый пользователь
     * @return - результат операции (true - успех/false - неуспех)
     */
    boolean deletePerson(Person person);
    /**
     * Изменение пользователя
     * @param person - измененный пользователь
     */
    void editPerson(Person person);
    /**
     * Возвращает список всех пользователей
     * @return List<Person> 
     */
    List<Person> getPersons();

}
