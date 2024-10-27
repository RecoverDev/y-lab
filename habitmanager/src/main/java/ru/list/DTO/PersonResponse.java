package ru.list.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PersonResponse {
    /**
     * идентификатор пользователя в базе
     */
    @JsonProperty("id")
     int id;
    /**
     * имя пользователя
     */
    @JsonProperty("name")
    private String name;
    /*
     * E-Mail пользователя
     */
    @JsonProperty("email")
    private String email;
    /**
     * роль пользователя в приложении (1 - администратор, 0 - пользователь)
     */
    @JsonProperty("role")
    private int role;
    /**
     * блокировка пользователя
     */
    @JsonProperty("blocked")
    private boolean blocked;
}
