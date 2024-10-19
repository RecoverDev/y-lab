package ru.list.Out;

import static java.lang.System.out;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ru.list.In.Response;

public class PersonView {
    Response response = new Response();

    public int ShowMenu() {

        String testMenu = """
            1. Мои привычки
            2. Новая привычка
            3. Удалить привычку
            4. Добавить выполнение привычки
            5. Статистика выполнения привычек
            ==============
            6. Личные данные
            0. Завершить работу

                """; 
        out.println(testMenu);

        return response.getInt("Введите номер пункта меню: ");
    }

    public List<String> addHabitView() {
        List<String> result = new ArrayList<>();

        String name = response.getSrting("Наименование привычки: ");
        String descr = response.getSrting("Описание привычки: ");
        String strPeriod = """
            Введите период повторения привычки:
            1. Ежедневно
            2. Еженедельно
            3. Ежемесячно
            4. Ежегодно

                """;
        out.println(strPeriod);
        int nomPeriod = response.getInt("Введите номер периода: ");

        result.add(name);
        result.add(descr);
        result.add(Integer.toString(nomPeriod));

        return result;
    }

    public int choiceHabit(List<String> habits) {
        for (String string : habits) {
            out.println(string);
        }
        out.println("");
        int result = response.getInt("Выберете номер привычки: ");
        return result;
    }

    public void showHabits(List<String> habits) {
        for (String string : habits) {
            out.println(string);
        }
        out.println("");
        response.getSrting("Нажмите ENTER");
    }

    public int choisePeriod(List<String> periods) {
        for (String string : periods) {
            out.println(string);
        }
        out.println("");
        return response.getInt("Введите номер периода: ");
    }

    public int selectFilterView() {
        String strFilter = """
            1. Фильтр по дате создания
            2. Фильтр по статусу

                """;
        out.println(strFilter);
        return response.getInt("Выберете фильтр: ");
    }

    public LocalDate getDate() {
        return response.getDate("Введите дату добавления привычек (ГГГГ-ММ-ДД): ");
    }

    public List<String> personalAccount() {
        String name = response.getSrting("Введите новое имя: ");
        String email = response.getSrting("Введите новый адрес электронной почты: ");
        String password = response.getSrting("Введите новый пароль: ");

        List<String> result = List.of(name, email, password);

        return result;
    }

}
