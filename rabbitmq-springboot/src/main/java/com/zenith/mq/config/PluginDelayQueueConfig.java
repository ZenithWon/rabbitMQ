package com.zenith.mq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class PluginDelayQueueConfig {
    public static final String DELAY_EXCHANGE_NAME="delay.exchange";
    public static final String DELAY_QUEUE_NAME="delay.queue";
    public static final String ROUTING_KEY_NAME="delay.routingKey";

    public static final String DELAY_EXCHANGE_TYPE="x-delayed-message";

    @Bean(DELAY_EXCHANGE_NAME)
    public CustomExchange delayExchange(){
        Map<String,Object> args=new HashMap<>();
        args.put("x-delayed-type","direct");
        return new CustomExchange(DELAY_EXCHANGE_NAME,DELAY_EXCHANGE_TYPE,true,false,args);
    }

    @Bean(DELAY_QUEUE_NAME)
    public Queue delayQueue(){
        return QueueBuilder.durable(DELAY_QUEUE_NAME).build();
    }

    @Bean(ROUTING_KEY_NAME)
    public Binding delayBinding(@Qualifier(DELAY_EXCHANGE_NAME) CustomExchange exchange,
                                @Qualifier(DELAY_QUEUE_NAME) Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_NAME).noargs();
    }

}
