package ru.list.Repository;

import java.util.List;

import ru.list.Model.Person;

/**
 * хранилище записей пользователей
 */
public interface PersonRepository {
    
    /**
     * сохранение пользователя
     * @param person - пользователь 
     * @return - результат операции (true - успех/false - неуспех)
     */
    boolean save(Person person);
    /**
     * удаление пользователя
     * @param person - пользователь 
     * @return - результат операции (true - успех/false - неуспех)
     */
    boolean delete(Person person);
    /**
     * поиск пользователя по E-Mail и паролю
     * @param email - E-Mail
     * @param password - пароль
     * @return - объект класса Person или null
     */
    Person findByEmailAndPassword(String email, String password);
    /**
     * вщзвращает пользователя по паролю
     * @param password - пароль пользователя
     * @return - объкт класса Person
     */
    Person findByPassword(String password);
    /**
     * возвращает список всех зарегистрированных пользователей
     * @return List<Person>
     */
    List<Person> findAll();
    /**
     * сообщает есть ли такой пользователь в хранилище
     * @param person - пользователь
     * @return true - есть такой пользователь, false - нет
     */
    boolean exist(Person person);

}
