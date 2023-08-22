package com.example.monitoringservice.repository;

import com.example.monitoringservice.entity.MethodExecutionTimeEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeCheckerRepository extends MongoRepository<MethodExecutionTimeEvent, String> {
    List<MethodExecutionTimeEvent> findAllBy();
    List<MethodExecutionTimeEvent> findByMethodName(String name);
}
