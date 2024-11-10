package ru.list.habitmanager.Service;

import java.util.List;

import ru.list.habitmanager.Model.Person;
import ru.list.habitmanager.Model.SingRespose;


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
     * @param id - идентификатор удаляемого пользователя
     * @return - результат операции (true - успех/false - неуспех)
     */
    boolean deletePerson(int id);
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
    /**
     * получение пользователя по ID
     * @param id - ID пользователя
     * @return - Person
     */
    Person getPersonById(int id);
    /**
     * получение пользователя по логину и паролю
     * @param email - E-mail
     * @param password - пароль
     * @return - пользователь
     */
    Person getPersonByEmailAndPassword(String email, String password);
    /**
     * получение текущего пользователя из Spring.Security
     * @return - Person
     */
    Person getCurrentPerson();
    /**
     * авторизация пользователя
     * @param respose объект класса SingResponse
     * @return - результат операции (true - успех/false - неуспех)
     */
    boolean authentication(SingRespose respose);
}
