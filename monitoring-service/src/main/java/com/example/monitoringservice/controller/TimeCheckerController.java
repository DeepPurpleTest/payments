package com.example.monitoringservice.controller;

import com.example.monitoringservice.entity.MethodExecutionTimeEvent;
import com.example.monitoringservice.service.TimeCheckerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class TimeCheckerController {
    private final TimeCheckerService timeCheckerService;

    @GetMapping("/findAll")
    public List<MethodExecutionTimeEvent> getAllEvents() {
        return timeCheckerService.getAllEvents();
    }
}
