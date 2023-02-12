package org.yascode.rabbitmqperso.config;

import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultExchangeConfig {

    @Value("${rabbit.default.queue.1}")
    private String defaultQueue_1;

    @Value("${rabbit.default.queue.2}")
    private String defaultQueue_2;

    private AmqpAdmin amqpAdmin;

    Queue createQueue1(){
        return new Queue(defaultQueue_1,true,false,false);
    }

    Queue createQueue2(){
        return new Queue(defaultQueue_2,true,false,false);
    }

    @Bean
    public AmqpTemplate defaultQueue1(ConnectionFactory connectionFactory, MessageConverter messageConverter){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        rabbitTemplate.setRoutingKey(defaultQueue_1);
        return rabbitTemplate;
    }

    @Bean
    public AmqpTemplate defaultQueue2(ConnectionFactory connectionFactory, MessageConverter messageConverter){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        rabbitTemplate.setRoutingKey(defaultQueue_2);
        return rabbitTemplate;
    }

    @Autowired
    public void setAmqpAdmin(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    @PostConstruct
    public void declareQueue() {
        amqpAdmin.declareQueue(createQueue1());
        amqpAdmin.declareQueue(createQueue2());
    }

}
