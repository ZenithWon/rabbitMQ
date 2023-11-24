package com.zenith.mq.five;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.zenith.mq.utils.RabbitMqUtils;

import java.io.IOException;
import java.util.UUID;

public class EmitLog {
    public static final String EXCHANGE_NAME="logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

        for(int i=0;i<5;i++){
            String msg= UUID.randomUUID().toString();
            channel.basicPublish(EXCHANGE_NAME,"",null,msg.getBytes());
            System.out.println("Sending msg:"+msg);
            Thread.sleep(500);
        }
    }
}
