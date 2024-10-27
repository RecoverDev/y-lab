package ru.list.Aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class HabitActionAspect {

    @Pointcut("execution(* HabitController.addHabit(String))")
    public void addHabitEvent() {
    }

    @Before("addHabitEvent")
    public void beforeAddHabitEvent() {
        System.out.println("Пользователь добавил привычку");
    }

    @Pointcut("execution(* HabitController.deleteHabit(int))")
    public void deleteHabitEvent() {
    }

    @Before("deleteHabitEvent")
    public void beforeDeleteHabitEvent() {
        System.out.println("Пользователь удалил привычку");
    }

    @Pointcut("execution(* HabitController.getHabits())")
    public void getHabitsEvent() {
    }

    @Before("getHabitsEvent")
    public void beforeGetHabitsEvent() {
        System.out.println("Пользователь получил список привычек");
    }

}
