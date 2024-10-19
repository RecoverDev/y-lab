package ru.list.In;

import java.util.Scanner;
import static java.lang.System.out;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * класс содержит медоты получающие информацию от пользователя
 */
public class Response {
    private static Scanner sc = new Scanner(System.in);

    /**
     * получает от пользователя строку
     * @param message - поясняющее сообщение, выводимое пользователю
     * @return строку введенную пользователем
     */
    public String getSrting(String message) {
        String result = "";
        out.print(message);
        if (sc.hasNextLine()) {
            result = sc.nextLine();
        }
        return result;
    }

    /**
     * получает от пользователя число
     * @param message - поясняющее сообщение, выводимое пользователю
     * @return число введенное пользователем
     */
    public int getInt(String message) {
        int result = 0;
        out.print(message);
        if (sc.hasNextInt()) {
            result = sc.nextInt();
        }
        //добавлено дополнительное считывание строки
        //из-за особенностей работы nextInt (функция оставляет после себя 0Dh 0Ah)
        sc.nextLine();
        return result;
    }

    /**
     * получает от пользователя дату
     * @param message - поясняющее сообщение
     * @return - дата, введенная пользователем
     */
    public LocalDate getDate(String message) {
        LocalDate result = null;
        out.print(message);
        if (sc.hasNextLine()) {
            result = LocalDate.parse(sc.nextLine()) ;
        }
        return result;
    }

    
    /**
     * получает от пользователя время
     * @param message - поясняющее сообщение
     * @return - время, введенное пользователем
     */
    public LocalTime getTime(String message) {
        LocalTime result = null;
        out.print(message);
        if (sc.hasNextLine()) {
            result = LocalTime.parse(sc.nextLine());
        }
        return result;
    }

}
