package ru.list.habitmanager.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * класс описывает пользователя приложения
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    /**
     * идентификатор пользователя в базе
     */
    int id;
    /**
     * имя пользователя
     */
     private String username;
    /*
     * E-Mail пользователя
     */
    private String email;
    /**
     * пароль пользователя, с которым он входит в приложение
     */
    private String password;
    /**
     * роль пользователя в приложении (1 - администратор, 0 - пользователь)
     */
    private Role role;
    /**
     * блокировка пользователя
     */
    private boolean blocked;

    
    /**
     * переопределили equals и hashCode для того, чтобы использовать HashSet
     * в Repository
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if ( object instanceof Person person) {
            return this.email.equals(person.email) && this.password.equals(person.getPassword());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.getEmail().hashCode() + this.getPassword().hashCode();
    }

}
