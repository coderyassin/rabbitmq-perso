package org.yascode.rabbitmqperso.config;

import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutExchangeConfig {

    private AmqpAdmin amqpAdmin;

    @Value("${rabbit.fanout1.queue}")
    private String fanoutQueue1;

    @Value("${rabbit.fanout2.queue}")
    private String fanoutQueue2;

    @Value("${rabbit.fanout3.queue}")
    private String fanoutQueue3;

    @Value("${rabbit.fanout.exchange}")
    private String exchange;

    Queue createFanoutQueue1(){
        return new Queue(fanoutQueue1,true,false,false);
    }

    Queue createFanoutQueue2(){
        return new Queue(fanoutQueue2,true,false,false);
    }

    Queue createFanoutQueue3(){
        return new Queue(fanoutQueue3,true,false,false);
    }

    FanoutExchange createFanoutExchange(){
        return new FanoutExchange(exchange,true,false);
    }

    @Bean
    public AmqpTemplate fanoutQueue(ConnectionFactory connectionFactory, MessageConverter messageConverter){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        rabbitTemplate.setExchange(exchange);
        return rabbitTemplate;
    }

    @PostConstruct
    public void init(){
        amqpAdmin.declareQueue(createFanoutQueue1());
        amqpAdmin.declareQueue(createFanoutQueue2());
        amqpAdmin.declareQueue(createFanoutQueue3());

        amqpAdmin.declareExchange(createFanoutExchange());
    }

    @Autowired
    public void setAmqpAdmin(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

}
