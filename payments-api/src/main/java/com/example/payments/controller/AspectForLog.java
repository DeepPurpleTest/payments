package com.example.payments.controller;

import com.example.monitoringservice.entity.MethodExecutionTimeEvent;
import com.example.monitoringservice.service.TimeCheckerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AspectForLog {
    private long beforeExecution;
    private final TimeCheckerService timeCheckerService;

    @Pointcut("execution(public * com.example.payments.controller.client.ClientPaymentController.createTransaction(..))")
    public void callAtMyControllerPublic() {
    }

    @Before("callAtMyControllerPublic()")
    public void beforeCallMethod() {
        beforeExecution = System.currentTimeMillis();
    }

    @After("callAtMyControllerPublic()")
    public void afterCallMethod(JoinPoint jp) {
        long afterExecution = System.currentTimeMillis();
        timeCheckerService.saveEvent(MethodExecutionTimeEvent.builder()
                .methodName(jp.getSignature().getName())
                .executionTime(afterExecution - beforeExecution)
                .build());
    }
}
