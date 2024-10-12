package ru.list.In;

import static java.lang.System.out;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PersonView {
    Response response = new Response();

    public int ShowMenu() {

        out.println("1. Мои привычки");
        out.println("2. Новая привычка");
        out.println("3. Удалить привычку");
        out.println("4. Добавить выполнение привычки");
        out.println("5. Статистика выполнения привычек");
        out.println("==============");
        out.println("6. Личные данные");
        out.println("0. Завершить работу");
        out.println("");

        return response.getInt("Введите номер пункта меню: ");
    }

    public List<String> addHabitView() {
        List<String> result = new ArrayList<>();

        String name = response.getSrting("Наименование привычки: ");
        String descr = response.getSrting("Описание привычки: ");
        out.println("Введите период повторения привычки:");
        out.println("1. Ежедневно");
        out.println("2. Еженедельно");
        out.println("3. Ежемесячно");
        out.println("4. Ежегодно");
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
        out.println("1. Фильтр по дате создания");
        out.println("2. Фильтр по статусу");
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
