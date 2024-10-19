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
        String txtTitle = """
            \033[H\033[J
            Программа \"Habit Manager\"
            для выхода нажмите \"0\"

                """;
        out.print(txtTitle);
    }

    /**
     * выводит сообщение по окончании работы программы
     */
    public static void showFinal() {
        out.println("Работа программы завершена");
    }

}
