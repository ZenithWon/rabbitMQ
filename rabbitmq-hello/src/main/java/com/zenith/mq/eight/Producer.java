package com.zenith.mq.eight;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.zenith.mq.utils.RabbitMqUtils;

import java.util.UUID;

public class Producer {
    public static final String NORMAL_EXCHANGE="normal_exchange";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        channel.exchangeDeclare(NORMAL_EXCHANGE , BuiltinExchangeType.DIRECT);

//        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().expiration("10000").build();

        for (int i = 0; i < 10; i++) {
            String msg = UUID.randomUUID().toString();
            channel.basicPublish(NORMAL_EXCHANGE , "normal" , null , msg.getBytes());
            System.out.println("Sending msg:" + msg);
        }
    }

}
