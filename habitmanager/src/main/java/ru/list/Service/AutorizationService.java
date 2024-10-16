package ru.list.Service;

import ru.list.Model.Person;

/**
 * Сервис авторизации пользователя в прогирамме и выхода из системы
 */
public interface AutorizationService {

    /**
     * авторизация пользователя
     * @param login - email пользователя
     * @param password - пароль пользователя
     * @return - объект класса Person или null
     */
    Person autorizate(String login, String password);

}
