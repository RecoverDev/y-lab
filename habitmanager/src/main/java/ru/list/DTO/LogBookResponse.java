package ru.list.DTO;

import java.time.LocalDate;

import lombok.Data;

@Data
public class LogBookResponse {
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
     private int habit_id;


}
