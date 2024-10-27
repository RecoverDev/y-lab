package ru.list.DTO;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class LogBookResponse {
    /**
     * идентификатор записи
     */
    @JsonProperty("id")
     int id;
    /**
     * дата выполнения привычки
     */
    @JsonProperty("date")
     private LocalDate date;
    /**
     * выполненная привычка
     */
    @JsonProperty("habit_id")
    @JsonFormat(pattern = "yyy-MM-dd")
     private int habit_id;


}
