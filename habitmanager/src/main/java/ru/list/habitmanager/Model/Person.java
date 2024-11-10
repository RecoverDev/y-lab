package ru.list.habitmanager.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "person", schema = "habit")
public class Person {

    /**
     * идентификатор пользователя в базе
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;
    /**
     * имя пользователя
     */
    @Column(name = "username")
     private String username;
    /*
     * E-Mail пользователя
     */
    @Column(name = "email")
    private String email;
    /**
     * пароль пользователя, с которым он входит в приложение
     */
    @Column(name = "password")
    private String password;
    /**
     * роль пользователя в приложении (1 - администратор, 0 - пользователь)
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;
    /**
     * блокировка пользователя
     */
    @Column(name = "blocked")
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
