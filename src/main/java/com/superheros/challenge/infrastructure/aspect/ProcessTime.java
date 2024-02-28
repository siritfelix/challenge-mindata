package com.superheros.challenge.infrastructure.aspect;

import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@Aspect
public class ProcessTime {
    private long initTime;
    @Autowired
    private HttpServletRequest request;

    @Pointcut("@annotation(com.superheros.challenge.infrastructure.aspect.Time)")
    public void timeAnnotation() {

    }

    @Around("timeAnnotation()")
    public Object time(ProceedingJoinPoint pjp) throws Throwable {
        initTime = new Date().getTime();
        Object obj = pjp.proceed();
        log.info(() -> String.format(">>>>>>> TIME REQUEST---> METHOD:%s, PATH:%s, (%s:%s), TIME:%d ms",
                request.getMethod(),
                request.getServletPath(),
                pjp.getSignature().getDeclaringTypeName(), pjp.getSignature().getName(),
                new Date().getTime() - initTime));

        return obj;
    }

    @AfterThrowing(pointcut = "timeAnnotation()", throwing = "exception")
    public void apiResponseExceptionLog(JoinPoint jp, Exception exception) {

        log.info(() -> String.format(">>>>>>> Exepcion--->  METHOD:%s, PATH:%s, TIME %d ms", request.getMethod(),
                request.getServletPath(),
                new Date().getTime() - initTime));

    }

}
