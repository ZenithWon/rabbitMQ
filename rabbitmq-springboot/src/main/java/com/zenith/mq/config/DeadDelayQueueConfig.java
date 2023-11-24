package com.zenith.mq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DeadDelayQueueConfig {
    public static final String NORMAL_EXCHANGE="normal_exchange";
    public static final String DELAY_EXCHANGE="dead_exchange";

    public static final String DELAY_QUEUE="delay_queue";
    public static final String DELAY_QUEUE_DEFAULT="delay_queue_default";
    public static final String DELAY_QUEUE_PLUS="delay_queue_plus";
    public static final String DELAY_QUEUE_HANDLER="delay_queue_handler";

    public static final String BINDING_NORMAL="diy";
    public static final String BINDING_NORMAL_DEFAULT="ten";
    public static final String BINDING_NORMAL_PLUS="twenty";
    public static final String BINDING_DEAD="dead";

    @Bean(NORMAL_EXCHANGE)
    public DirectExchange normalExchange(){
        return new DirectExchange(NORMAL_EXCHANGE);
    }

    @Bean(DELAY_EXCHANGE)
    public DirectExchange delayExchange(){
        return new DirectExchange(DELAY_EXCHANGE);
    }

    @Bean(DELAY_QUEUE)
    public Queue delayQueue(){
        Map<String,Object> args=new HashMap<>();
        args.put("x-dead-letter-exchange", DELAY_EXCHANGE);
        args.put("x-dead-letter-routing-key", BINDING_DEAD);
        return QueueBuilder.durable(DELAY_QUEUE).withArguments(args).build();
    }

    @Bean(DELAY_QUEUE_DEFAULT)
    public Queue delayQueueDefault(){
        Map<String,Object> args=new HashMap<>();
        args.put("x-dead-letter-exchange", DELAY_EXCHANGE);
        args.put("x-dead-letter-routing-key", BINDING_DEAD);
        args.put("x-message-ttl", 10000);
        return QueueBuilder.durable(DELAY_QUEUE_DEFAULT).withArguments(args).build();
    }

    @Bean(DELAY_QUEUE_PLUS)
    public Queue delayQueuePlus(){

        Map<String,Object> args=new HashMap<>();
        args.put("x-dead-letter-exchange", DELAY_EXCHANGE);
        args.put("x-dead-letter-routing-key", BINDING_DEAD);
        args.put("x-message-ttl", 20000);
        return QueueBuilder.durable(DELAY_QUEUE_PLUS).withArguments(args).build();
    }

    @Bean(DELAY_QUEUE_HANDLER)
    public Queue delayQueueHandler(){
        return QueueBuilder.durable(DELAY_QUEUE_HANDLER).build();
    }

    @Bean(BINDING_NORMAL_DEFAULT)
    public Binding bindingNormalDefault(@Qualifier(DELAY_QUEUE_DEFAULT) Queue queue,
                                        @Qualifier(NORMAL_EXCHANGE) DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(BINDING_NORMAL_DEFAULT);
    }

    @Bean(BINDING_NORMAL_PLUS)
    public Binding bindingNormalPlus(@Qualifier(DELAY_QUEUE_PLUS) Queue queue,
                                        @Qualifier(NORMAL_EXCHANGE) DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(BINDING_NORMAL_PLUS);
    }

    @Bean(BINDING_NORMAL)
    public Binding bindingNormal(@Qualifier(DELAY_QUEUE) Queue queue,
                                     @Qualifier(NORMAL_EXCHANGE) DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(BINDING_NORMAL);
    }

    @Bean(BINDING_DEAD)
    public Binding bindingDead(@Qualifier(DELAY_QUEUE_HANDLER) Queue queue,
                                     @Qualifier(DELAY_EXCHANGE) DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(BINDING_DEAD);
    }


}
