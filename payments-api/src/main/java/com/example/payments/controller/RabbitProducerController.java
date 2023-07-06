package com.example.payments.controller;

import com.example.payments.service.PaymentMailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rabbit")
@RequiredArgsConstructor
public class RabbitProducerController {
    private final PaymentMailService paymentMailService;

//    @GetMapping("/send")
//    public ResponseEntity<String> sendMessage(@RequestParam("message") String message) {
//        paymentMailService.sendMessage(message);
//        return ResponseEntity.ok("Message sent to RabbitMQ");
//    }
}
