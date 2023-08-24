package com.example.payments.configuration.aop;

import com.example.monitoringservice.entity.MethodExecutionTimeEvent;
import com.example.monitoringservice.service.TimeCheckerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AspectForExecutionTimeAnnotation {
    private final TimeCheckerService timeCheckerService;

    @Around("@annotation(com.example.payments.util.annotation.LogExecutionTime)")
    public Object saveExecutionTimeToMongo(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object proceed = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - start;

        timeCheckerService.saveEvent(MethodExecutionTimeEvent.builder()
                .methodName(joinPoint.getSignature().toShortString())
                .executionTime(executionTime)
                .build());

        return proceed;
    }
}
