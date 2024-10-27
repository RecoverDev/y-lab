package ru.list.Aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LoginAspect {

    @Pointcut("execution(* AutorizationController.login(String,String))")
    public void loginEvent() {
    }

    @Before("loginEvent") 
    public void beforeLoginEvent(){
        System.out.println("Авторизация пользователя");
    }

    @Pointcut("execution(* AutorizationController.logout())")
    public void logoutEvent() {
    }

    @Before("logoutEvent") 
    public void beforeLogoutEvent(){
        System.out.println("Пользователь завершил работу с системой");
    }



}
