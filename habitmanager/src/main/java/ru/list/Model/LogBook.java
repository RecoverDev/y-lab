package ru.list.Model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * класс описывает журнал регистрации выполнения привычек пользователей
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogBook {

    /**
     * идентификатор записи
     */
    int id;
    /**
     * дата выполнения привычки
     */
    private LocalDate date;
    /**
     * выполненная привычка
     */
    private Habit habit;

}
