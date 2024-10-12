package ru.list.In;

import static java.lang.System.out;

import java.util.List;


public class StatisticView {
    Response response = new Response();

    public int showMenu() {
        out.println("1. Текущая серия выполнения привычек");
        out.println("2. Процент успешного выполнения привычек");
        out.println("3. Прогресс выполнения привычки");
        out.println("4. Статискина выполнения привычки");

        out.println("");

        return response.getInt("Введите номер пункта меню: ");
    }

    public void showStatistic(List<String> records) {
        for (String string : records) {
            out.println(string);
        }
        out.println("");
        response.getSrting("Нажмите ENTER");
    }

    public void showPercent(double percent) {
        out.println(String.format("Процент успешного выполнения привычек: %d2\\%",percent));
        response.getSrting("Нажмите ENTER");

    }

    public int choicePeriod(List<String> period) {
        for (String string : period) {
            out.println(string);
        }
        out.println("");

        return response.getInt("Введите номер нужного периода отчета: ");
    }
}
