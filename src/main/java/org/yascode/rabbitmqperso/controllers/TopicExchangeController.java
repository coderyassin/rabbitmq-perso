package org.yascode.rabbitmqperso.controllers;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yascode.rabbitmqperso.models.MessageDto;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/topic")
public class TopicExchangeController {

    private AmqpTemplate topicQueue;

    public TopicExchangeController(AmqpTemplate topicQueue) {
        this.topicQueue = topicQueue;
    }

    @GetMapping("/message/{key}")
    public String sendMessage(@PathVariable String key) throws Exception {
        MessageDto message = new MessageDto("Topic", LocalDateTime.now());
        topicQueue.convertAndSend(key,message);
        return "Success Topic";
    }
}
