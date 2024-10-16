package ru.list.Out;

import static java.lang.System.out;

import java.util.List;

import ru.list.In.Response;

public class AdminView {
    Response response = new Response();

    public int showMenu() {
        out.println("1. Список пользователей");
        out.println("2. Удалить пользователя");
        out.println("3. Заблокировать пользователя");
        out.println("");
        out.println("0. Завершить работу");
        out.println("");

        return response.getInt("Введите номер пункта меню: ");
    }

    public void showPersons(List<String> persons) {
        for (String string : persons) {
            out.println(string);
        }
        out.println("");
        response.getSrting("Нажмите ENTER");

    }

    public int choicePerson(List<String> persons) {
        for (String string : persons) {
            out.println(string);
        }
        out.println("");
        int result = response.getInt("Выберете номер пользователя: ");
        return result;

    }

}
