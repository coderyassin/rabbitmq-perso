package org.yascode.rabbitmqperso.config;

import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectExchangeConfig {

    private AmqpAdmin amqpAdmin;

    @Value("${rabbit.direct1.queue}")
    private String directQueue1;

    @Value("${rabbit.direct2.queue}")
    private String directQueue2;

    @Value("${rabbit.direct3.queue}")
    private String directQueue3;

    @Value("${rabbit.direct1.bi}")
    private String binding1;

    @Value("${rabbit.direct2.bi}")
    private String binding2;

    @Value("${rabbit.direct3.bi}")
    private String binding3;

    @Value("${rabbit.direct.exchange}")
    private String exchange;

    Queue createDirectQueue1(){
        return new Queue(directQueue1,true,false,false);
    }

    Queue createDirectQueue2(){
        return new Queue(directQueue2,true,false,false);
    }

    Queue createDirectQueue3(){
        return new Queue(directQueue3,true,false,false);
    }

    DirectExchange createDirectExchange(){
        return new DirectExchange(exchange,true,false);
    }

    Binding createDirectBinding1(){
        return BindingBuilder.bind(createDirectQueue1()).to(createDirectExchange()).with(binding1);
    }

    Binding createDirectBinding2(){
        return BindingBuilder.bind(createDirectQueue2()).to(createDirectExchange()).with(binding2);
    }

    Binding createDirectBinding3(){
        return BindingBuilder.bind(createDirectQueue3()).to(createDirectExchange()).with(binding3);
    }

    @PostConstruct
    public void init(){
        amqpAdmin.declareQueue(createDirectQueue1());
        amqpAdmin.declareQueue(createDirectQueue2());
        amqpAdmin.declareQueue(createDirectQueue3());

        amqpAdmin.declareExchange(createDirectExchange());

        amqpAdmin.declareBinding(createDirectBinding1());
        amqpAdmin.declareBinding(createDirectBinding2());
        amqpAdmin.declareBinding(createDirectBinding3());
    }

    @Autowired
    public void setAmqpAdmin(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }
}
