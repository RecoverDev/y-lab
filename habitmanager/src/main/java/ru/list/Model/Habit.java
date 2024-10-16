package ru.list.Model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * класс описывает привычки пользователей
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Habit {

    /**
     * название привычки
     */
    private String name;
    /**
     * описание привычки
     */
    private String description;
    /**
     * пользователь, которому принадлижит привычка
     */
    private Person person;
    /**
     * период повторения привычки
     */
    private Period period;
    /**
     * дата добавления (регистрации) привычки
     */
    private LocalDate registration;

}
