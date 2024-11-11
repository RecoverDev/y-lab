package ru.list.habitmanager.Model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * класс описывает привычки пользователей
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Habit {

    /**
     * идентификатор привычки
     */
    private int id;
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
