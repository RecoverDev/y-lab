package ru.list.DTO;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class HabitResponse {
    /**
     * идентификатор привычки
     */
    @JsonProperty("id")
     private int id;
    /**
     * название привычки
     */
    @JsonProperty("name")
     private String name;
    /**
     * описание привычки
     */
    @JsonProperty("description")
     private String description;
    /**
     * пользователь, которому принадлижит привычка
     */
    @JsonProperty("person_id")
     private int person_id;
    /**
     * период повторения привычки
     */
    @JsonProperty("period_id")
     private int period_id;
    /**
     * дата добавления (регистрации) привычки
     */
    @JsonProperty("registration")
    @JsonFormat(pattern = "yyy-MM-dd")
     private String registration;


}
