package ru.list.Out;

import static java.lang.System.out;

import java.util.ArrayList;
import java.util.List;

import ru.list.In.Response;


/**
 * Класс обеспечивает авторизацию и регистрацию пользователя
 */
public class AutorizateView {
    private Response response = new Response();

    /**
     * Стартовый экран
     * @return - номер пункта меню
     */
    public int StartView() {
        out.println("1. Вход");
        out.println("2. Регистрация");
        out.println();
        out.println("0. Завершить работу");
        out.println();

        return response.getInt("Введите номер пункта меню: ");
    }

    /**
     * Экран авторизации пользователя
     * @return - список строк (логин и пароль)
     */
    public List<String> LoginView() {
        List<String> result = new ArrayList<>();

        String login = response.getSrting("Введите адрес электронной почты: ");
        String password = response.getSrting("Введите пароль: ");
        result.add(login);
        result.add(password);

        return result;
    }

    /**
     * Экран регистрации нового пользователя
     * @return - список строк (параметры пользователя)
     */
    public List<String> RegistrationView() {
        List<String> result = new ArrayList<>();

        String name = response.getSrting("Введите имя: ");
        String login = response.getSrting("Введите адрес электронной почты: ");
        String password = response.getSrting("Введите пароль: ");
        int role = 2;
        while (role != 0 & role != 1) {
            role = response.getInt("Введите роль пользователя (0 - пользователь, 1 - админимтратор): ");
        }
        result.add(name);
        result.add(login);
        result.add(password);
        result.add(Integer.toString(role));

        return result;
    }

}
