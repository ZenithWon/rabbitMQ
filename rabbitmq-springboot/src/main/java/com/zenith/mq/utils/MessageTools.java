package com.zenith.mq.utils;

import com.zenith.mq.config.ConfirmMqConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.UUID;

public class MessageTools {
    public static String sendRandomMessage(RabbitTemplate rabbitTemplate,String exchange,String routingKey){
        String payload= UUID.randomUUID().toString().replace("-","");
        String msg ="Send time: " + LocalDateTime.now()+", payload: "+payload;

        CorrelationData correlationData = new CorrelationData();
        String id= UUID.randomUUID().toString();
        correlationData.setId(id);
        correlationData.setReturnedMessage(new Message(msg.getBytes(StandardCharsets.UTF_8)));

        rabbitTemplate.convertAndSend(exchange,routingKey,msg,correlationData);
        return String.format("Send message=>[%s], exchange: [%s], routing key: [%s]",
                msg,exchange,routingKey);
    }

    public static String sendRandomMessageWithTtl(RabbitTemplate rabbitTemplate,String exchange,String routingKey,Integer ttl){
        String payload= UUID.randomUUID().toString().replace("-","");

        String msg ="Send time: " + LocalDateTime.now()+", ttl: "+ttl+"s"+", payload: "+payload;

        CorrelationData correlationData = new CorrelationData();
        String id= UUID.randomUUID().toString();
        correlationData.setId(id);
        correlationData.setReturnedMessage(new Message(msg.getBytes(StandardCharsets.UTF_8)));

        rabbitTemplate.convertAndSend(exchange,routingKey,msg,
                (message)->{
                    message.getMessageProperties().setDelay(ttl*1000);
                    return  message;
                },correlationData);
        return String.format("Send message=>[%s], exchange: [%s], routing key: [%s]",
                msg,exchange,routingKey);
    }

    public static String sendRandomMessageWithPriority(RabbitTemplate rabbitTemplate,String exchange,String routingKey,Integer priority){
        String payload= UUID.randomUUID().toString().replace("-","");

        String msg ="Send time: " + LocalDateTime.now()+", priority: "+priority+", payload: "+payload;

        CorrelationData correlationData = new CorrelationData();
        String id= UUID.randomUUID().toString();
        correlationData.setId(id);
        correlationData.setReturnedMessage(new Message(msg.getBytes(StandardCharsets.UTF_8)));

        rabbitTemplate.convertAndSend(exchange,routingKey,msg,
                (message)->{
                    message.getMessageProperties().setPriority(priority);
                    return  message;
                },correlationData);
        return String.format("Send message=>[%s], exchange: [%s], routing key: [%s]",
                msg,exchange,routingKey);
    }

    public static String generateMessage(){
        String payload= UUID.randomUUID().toString();
        return "Send time: " + LocalDateTime.now()+", payload: "+payload;
    }
}
