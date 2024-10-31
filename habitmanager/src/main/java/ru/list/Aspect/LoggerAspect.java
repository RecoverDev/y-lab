package ru.list.Aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;

public class LoggerAspect {
    @Pointcut("execution(* * (..))")
    public void logPersonAction() {
    }

    @Around("logPersonAction()") 
    public Object aroundLoginEvent(ProceedingJoinPoint joinPoint) {
        long beginJob = System.currentTimeMillis();
        Object result = null;

        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            System.out.println("Ошибка выполнения функции [" + joinPoint.getSignature() + "]");
        }

        long leadTime = System.currentTimeMillis() - beginJob;
        System.out.println( String.format("Время выполнения функции [%s] - %d ms",joinPoint.getSignature(), leadTime));
        return result;
    }

}
