package com.zenith.mq.eight;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.zenith.mq.utils.RabbitMqUtils;

import java.util.HashMap;
import java.util.Map;

public class Consumer02 {
    public static final String DEAD_EXCHANGE="dead_exchange";
    public static final String DEAD_QUEUE="dead_queue";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMqUtils.getChannel();

        channel.exchangeDeclare(DEAD_EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.queueDeclare(DEAD_QUEUE,false,false,false,null);


        channel.queueBind(DEAD_QUEUE,DEAD_EXCHANGE,"dead");

        DeliverCallback deliverCallback=(consumerTag, message)->{
            System.out.println("Receive message: "+new String(message.getBody()));
        };

        System.out.println("Consumer02 waiting for dead queue...");
        channel.basicConsume(DEAD_QUEUE,true,deliverCallback,tag->{});
    }
}
