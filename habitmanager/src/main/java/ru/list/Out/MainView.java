package ru.list.Out;

import static java.lang.System.out;

/**
 * класс содержит методы, выводящие сообщения при работе с сервисом MainService
 */
public class MainView {

    /**
     * выводит заголовок программы
     */
    public static void showTitle() {
        out.print("\033[H\033[J");
        out.println("Программа \"Habit Manager\"");
        out.println("для выхода нажмите \"0\"");
        out.println();
    }

    /**
     * выводит сообщение по окончании работы программы
     */
    public static void showFinal() {
        out.println("Работа программы завершена");
    }

}
