package ru.list.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonResponse {
    /**
     * идентификатор пользователя в базе
     */
     int id;
    /**
     * имя пользователя
     */
    private String name;
    /*
     * E-Mail пользователя
     */
    private String email;
    /**
     * роль пользователя в приложении (1 - администратор, 0 - пользователь)
     */
    private int role;
    /**
     * блокировка пользователя
     */
    private boolean blocked;
}
