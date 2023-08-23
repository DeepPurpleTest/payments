package com.example.monitoringservice.service;

import com.example.monitoringservice.entity.MethodExecutionTimeEvent;
import com.example.monitoringservice.repository.TimeCheckerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TimeCheckerService {
    private final TimeCheckerRepository timeCheckerRepository;

    public List<MethodExecutionTimeEvent> getAllEvents() {
        return timeCheckerRepository.findAllBy();
    }

    public List<MethodExecutionTimeEvent> getAllEventsByMethodName(String methodName) {
        return timeCheckerRepository.findByMethodName(methodName);
    }

    public void saveEvent(MethodExecutionTimeEvent event) {
        log.info("TimeCheckerService.saveEvent() -> start");
        timeCheckerRepository.save(event);
        System.out.println(timeCheckerRepository.findAllBy().size());
        log.info("TimeCheckerService.saveEvent() -> end");

    }
}
