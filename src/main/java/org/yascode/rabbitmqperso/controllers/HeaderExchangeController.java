package org.yascode.rabbitmqperso.controllers;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.yascode.rabbitmqperso.models.MessageDto;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/header")
public class HeaderExchangeController {

    private AmqpTemplate headerQueue;

    public HeaderExchangeController(AmqpTemplate headerQueue) {
        this.headerQueue = headerQueue;
    }

    @GetMapping("/message") // local8080/header/message?debug=sdf&error=dfs
    public String sendMessage(
            @RequestParam(name = "error",required = false) String error,
            @RequestParam(name = "warning",required = false) String warning,
            @RequestParam(name = "debug",required = false) String debug
    ){
        MessageDto message = new MessageDto("Header", LocalDateTime.now());
        MessageBuilder messageBuilder = MessageBuilder.withBody(message.toString().getBytes());
        if(error != null){
            messageBuilder.setHeader("error",error);
        }
        if(warning != null){
            messageBuilder.setHeader("warning",warning);
        }
        if(debug != null){
            messageBuilder.setHeader("debug",debug);
        }
        headerQueue.convertAndSend(messageBuilder.build());
        return "Success Header Exchange";
    }
}
