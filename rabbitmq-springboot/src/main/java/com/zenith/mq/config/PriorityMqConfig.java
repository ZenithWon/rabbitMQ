package com.zenith.mq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
@Configuration
public class PriorityMqConfig {

    public static final String EXCHANGE_NAME="priority.exchange";
    public static final String QUEUE_NAME="priority.queue";
    public static final String ROUTING_KEY_NAME="priority";

    @Bean(EXCHANGE_NAME)
    public DirectExchange exchange(){
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean(QUEUE_NAME)
    public Queue queue(){
        Map<String,Object> args=new HashMap<>();
        args.put("x-max-priority",10);
        return QueueBuilder.durable(QUEUE_NAME).withArguments(args).build();
    }

    @Bean(ROUTING_KEY_NAME)
    public Binding binding(@Qualifier(EXCHANGE_NAME) DirectExchange exchange,
                           @Qualifier(QUEUE_NAME) Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_NAME);
    }
}
