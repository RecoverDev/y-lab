package ru.list.In;

import static java.lang.System.out;

public class AdminView {
    Response response = new Response();

    public int showMenu() {
        out.println("1. Список пользователей");
        out.println("2. Удалить пользователя");
        out.println("");
        out.println("0. Завершить работу");
        out.println("");

        return response.getInt("Введите номер пункта меню: ");
    }

}
