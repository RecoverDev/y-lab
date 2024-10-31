package ru.list.Aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

public class AuditAspect {

    @Pointcut("@annotation(Audit) && execution(* * (..))")
    public void auditPersonAction() {
    }

    @Before("auditPersonAction()") 
    public void aroundAuditEvent(ProceedingJoinPoint joinPoint) {
        System.out.println( String.format("Пользователь выполнил функцию [%s]",joinPoint.getSignature()));
    }
}
