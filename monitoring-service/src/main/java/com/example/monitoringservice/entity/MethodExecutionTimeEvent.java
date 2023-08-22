package com.example.monitoringservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.annotation.Id;

@Data
@Builder
@Jacksonized
@AllArgsConstructor
@NoArgsConstructor
public class MethodExecutionTimeEvent{
    @Id
    private String id;
    private String methodName;
    private long executionTime;
}
