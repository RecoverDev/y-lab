package ru.list.habitmanager.DTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class LogBookResponse {
    /**
     * идентификатор записи
     */
    private int id;
    /**
     * дата выполнения привычки
     */
     private LocalDate date;
    /**
     * выполненная привычка
     */
     private int habit_id;


}
