package com.zenith.mq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ConfirmMqConfig {
    public static final String EXCHANGE_NAME="confirm.exchange";
    public static final String QUEUE_NAME="confirm.queue";
    public static final String ROUTING_KEY_NAME="confirm";

    @Bean(EXCHANGE_NAME)
    public DirectExchange exchange(){
        Map<String,Object> args=new HashMap<>();
        args.put("alternate-exchange",BackupMqConfig.EXCHANGE_NAME);

        return ExchangeBuilder.directExchange(EXCHANGE_NAME).durable(true).withArguments(args).build();
    }

    @Bean(QUEUE_NAME)
    public Queue queue(){
        return QueueBuilder.durable(QUEUE_NAME).build();
    }

    @Bean
    public Binding binding(@Qualifier(EXCHANGE_NAME) DirectExchange exchange,
                           @Qualifier(QUEUE_NAME) Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_NAME);
    }

}
