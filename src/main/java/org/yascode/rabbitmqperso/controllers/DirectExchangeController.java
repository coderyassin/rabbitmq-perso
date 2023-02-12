package org.yascode.rabbitmqperso.controllers;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yascode.rabbitmqperso.models.MessageDto;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/direct")
public class DirectExchangeController {

    private AmqpTemplate directQueue;

    @Value("${rabbit.direct1.bi}")
    private String binding1;

    @Value("${rabbit.direct2.bi}")
    private String binding2;

    @Value("${rabbit.direct3.bi}")
    private String binding3;

    public DirectExchangeController(AmqpTemplate directQueue) {
        this.directQueue = directQueue;
    }

    @GetMapping("/message/{num}")
    public String sendMessage(@PathVariable Integer num) throws Exception {
        String key;
        if(num == 1){
            key = binding1;
        } else if (num == 2){
            key = binding2;
        } else if(num == 3){
            key = binding3;
        } else {
            throw new Exception("You must Enter 1,2 or 3 only");
        }
        MessageDto message = new MessageDto("direct", LocalDateTime.now());
        directQueue.convertAndSend(key,message);
        return "Success";
    }
}
