package org.yascode.rabbitmqperso.config;

import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HeaderExchangeConfig {

    private AmqpAdmin amqpAdmin;

    @Value("${rabbit.header1.queue}")
    private String headerQueue1;

    @Value("${rabbit.header2.queue}")
    private String headerQueue2;

    @Value("${rabbit.header3.queue}")
    private String headerQueue3;

    @Value("${rabbit.header.exchange}")
    private String exchange;

    Queue createHeaderQueue1(){
        return new Queue(headerQueue1,true,false,false);
    }

    Queue createHeaderQueue2(){
        return new Queue(headerQueue2,true,false,false);
    }

    Queue createHeaderQueue3(){
        return new Queue(headerQueue3,true,false,false);
    }

    Binding createHeaderBinding1(){
        return BindingBuilder.bind(createHeaderQueue1()).to(createHeaderExchange()).whereAny("error","warning").exist();
    }

    Binding createHeaderBinding2(){
        return BindingBuilder.bind(createHeaderQueue2()).to(createHeaderExchange()).whereAny("warning","debug").exist();
    }

    Binding createHeaderBinding3(){
        return BindingBuilder.bind(createHeaderQueue3()).to(createHeaderExchange()).whereAll("error","warning","debug").exist();
    }

    HeadersExchange createHeaderExchange(){
        return new HeadersExchange(exchange,true,false);
    }

    @Bean
    public AmqpTemplate headerQueue(ConnectionFactory connectionFactory, MessageConverter messageConverter){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        rabbitTemplate.setExchange(exchange);
        return rabbitTemplate;
    }

    @PostConstruct
    public void init(){
        amqpAdmin.declareQueue(createHeaderQueue1());
        amqpAdmin.declareQueue(createHeaderQueue2());
        amqpAdmin.declareQueue(createHeaderQueue3());

        amqpAdmin.declareExchange(createHeaderExchange());

        amqpAdmin.declareBinding(createHeaderBinding1());
        amqpAdmin.declareBinding(createHeaderBinding2());
        amqpAdmin.declareBinding(createHeaderBinding3());
    }

    @Autowired
    public void setAmqpAdmin(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

}
