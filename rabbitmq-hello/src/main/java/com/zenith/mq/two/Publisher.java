package com.zenith.mq.two;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import com.zenith.mq.utils.RabbitMqUtils;

import java.util.Scanner;
import java.util.UUID;

public class Publisher {
    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMqUtils.getChannel();

        channel.queueDeclare(QUEUE_NAME,true,false,false,null);

        for(int i=0;i<10;i++){
            String msg= UUID.randomUUID().toString();
            channel.basicPublish("",QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN ,msg.getBytes());
            System.out.println("Sending msg:"+msg);
            Thread.sleep(1000);
        }
    }
}
