package ru.list.habitmanager.Model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
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
@Entity
@Table(name = "habit", schema = "habit")
public class Habit {

    /**
     * идентификатор привычки
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    /**
     * название привычки
     */
    @Column(name = "name_habit")
     private String name;
    /**
     * описание привычки
     */
    @Column(name = "description")
    private String description;
    /**
     * пользователь, которому принадлижит привычка
     */
    @OneToOne
    private Person person;
    /**
     * период повторения привычки
     */
    @Column(name = "period")
    private Period period;
    /**
     * дата добавления (регистрации) привычки
     */
    @Column(name = "registration")
    private LocalDate registration;

}
