package com.zenith.mq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BackupMqConfig {
    public static final String EXCHANGE_NAME="backup.exchange";
    public static final String WARN_QUEUE_NAME="warn.queue";
    public static final String BACKUP_QUEUE_NAME="backup.queue";

    @Bean(EXCHANGE_NAME)
    public FanoutExchange exchange(){
        return new FanoutExchange(EXCHANGE_NAME);
    }

    @Bean(WARN_QUEUE_NAME)
    public Queue queue1(){
        return QueueBuilder.durable(WARN_QUEUE_NAME).build();
    }

    @Bean(BACKUP_QUEUE_NAME)
    public Queue queue2(){
        return QueueBuilder.durable(BACKUP_QUEUE_NAME).build();
    }

    @Bean
    public Binding binding1(@Qualifier(EXCHANGE_NAME) FanoutExchange exchange,
                           @Qualifier(WARN_QUEUE_NAME) Queue queue){
        return BindingBuilder.bind(queue).to(exchange);
    }

    @Bean
    public Binding binding2(@Qualifier(EXCHANGE_NAME) FanoutExchange exchange,
                           @Qualifier(BACKUP_QUEUE_NAME) Queue queue){
        return BindingBuilder.bind(queue).to(exchange);
    }
}
