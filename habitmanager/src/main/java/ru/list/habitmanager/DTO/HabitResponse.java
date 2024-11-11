package ru.list.habitmanager.DTO;

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
