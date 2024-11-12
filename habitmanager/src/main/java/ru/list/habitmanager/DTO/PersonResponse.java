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
public class PersonResponse {
    /**
     * идентификатор пользователя в базе
     */
     private int id;
    /**
     * имя пользователя
     */
    private String username;
    /*
     * E-Mail пользователя
     */
    private String email;
    /**
     * роль пользователя в приложении (1 - администратор, 0 - пользователь)
     */
    private String role;
    /**
     * блокировка пользователя
     */
    private boolean blocked;
}
