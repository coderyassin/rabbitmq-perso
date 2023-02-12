package org.yascode.rabbitmqperso.controllers;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yascode.rabbitmqperso.models.MessageDto;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/fanout")
public class FanoutExchangeController {

    private AmqpTemplate fanoutQueue;

    public FanoutExchangeController(AmqpTemplate fanoutQueue) {
        this.fanoutQueue = fanoutQueue;
    }

    @GetMapping("/message")
    public String sendMessage() throws Exception {
        MessageDto message = new MessageDto("direct", LocalDateTime.now());
        fanoutQueue.convertAndSend(message);
        return "Success Fanout";
    }
}
