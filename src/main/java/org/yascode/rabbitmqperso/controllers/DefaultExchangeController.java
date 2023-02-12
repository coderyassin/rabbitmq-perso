package org.yascode.rabbitmqperso.controllers;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yascode.rabbitmqperso.models.MessageDto;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/default-exchange")
public class DefaultExchangeController {

    private AmqpTemplate defaultQueue1;

    private AmqpTemplate defaultQueue2;

    public DefaultExchangeController(AmqpTemplate defaultQueue1, AmqpTemplate defaultQueue2) {
        this.defaultQueue1 = defaultQueue1;
        this.defaultQueue2 = defaultQueue2;
    }

    @GetMapping("/message1")
    public void sendMessage1(){
        MessageDto messageDto = new MessageDto("default-message1", LocalDateTime.now());
        defaultQueue1.convertAndSend(messageDto);
    }

    @GetMapping("/message2")
    public void sendMessage2(){
        MessageDto messageDto = new MessageDto("default-message2", LocalDateTime.now());
        defaultQueue2.convertAndSend(messageDto);
    }



}
