package ru.list.habitmanager.Model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * класс описывает журнал регистрации выполнения привычек пользователей
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
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
