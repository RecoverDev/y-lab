package ru.list.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HabitResponse {
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
     private int person_id;
    /**
     * период повторения привычки
     */
     private int period_id;
    /**
     * дата добавления (регистрации) привычки
     */
     private String registration;


}
