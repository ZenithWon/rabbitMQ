package com.zenith.mq.utils;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMqUtils {
    public static Channel getChannel(){
        ConnectionFactory factory=new ConnectionFactory();
        factory.setHost("124.70.180.4");
        factory.setUsername("admin");
        factory.setPassword("123456");
        factory.setPort(5672);
        Channel channel=null;

        try {
            Connection connection = factory.newConnection();
            channel = connection.createChannel();
        } catch (Exception e) {
            System.out.println("Create channel failed");
        }

        return channel;
    }

    public static DeliverCallback getDefaultDeliverCallback(String worker,int sleepTime){
        return (consumerTag, message)->{
            SleepUtils.sleep(sleepTime);
            System.out.println(worker+" deliver message: "+new String(message.getBody()));
        };
    }

    public static CancelCallback getDefaultCancelCallback(){
        return (consumerTag)->{
            System.out.println("Interrupt consume");
        };
    }
}
