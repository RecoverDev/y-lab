package ru.list.aspect_starter.Aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;

@Aspect
public class LoggerAspect {
    private final org.slf4j.Logger log = LoggerFactory.getLogger(LoggerAspect.class);

    @Pointcut("@annotation(EnableXXX) && execution(public * *(..))")
    public void logPersonAction() {
    }

    @Around("logPersonAction()") 
    public Object aroundLoginEvent(ProceedingJoinPoint joinPoint) {
        long beginJob = System.currentTimeMillis();
        Object result = null;

        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            log.error("Ошибка выполнения функции [" + joinPoint.getSignature() + "]");
            //System.out.println("Ошибка выполнения функции [" + joinPoint.getSignature() + "]");
        }

        long leadTime = System.currentTimeMillis() - beginJob;
        log.info(String.format("Время выполнения функции [%s] - %d ms",joinPoint.getSignature(), leadTime));
        //System.out.println( String.format("Время выполнения функции [%s] - %d ms",joinPoint.getSignature(), leadTime));
        return result;
    }

}
