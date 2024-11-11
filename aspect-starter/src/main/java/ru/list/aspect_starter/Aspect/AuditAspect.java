package ru.list.aspect_starter.Aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
@Aspect
public class AuditAspect {
    private final org.slf4j.Logger log = LoggerFactory.getLogger(AuditAspect.class);


    @Pointcut("execution(public * *(..))")
    public void auditPersonAction() {
    }

    @Before("auditPersonAction()") 
    public void aroundAuditEvent(ProceedingJoinPoint joinPoint) {

        log.info(String.format("Пользователь выполнил функцию [%s]",joinPoint.getSignature()));
    }
}
