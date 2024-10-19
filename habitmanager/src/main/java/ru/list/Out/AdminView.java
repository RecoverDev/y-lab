package ru.list.Out;

import static java.lang.System.out;

import java.util.List;

import ru.list.In.Response;

public class AdminView {
    Response response = new Response();

    public int showMenu() {
        String txtMenu = """
            1. Список пользователей
            2. Удалить пользователя
            3. Заблокировать пользователя

            0. Завершить работу

                """;
        out.println(txtMenu);

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
