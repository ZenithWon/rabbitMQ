package com.zenith.mq.one;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
    public static final String QUEUE_NAME="hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory=new ConnectionFactory();
        factory.setHost("124.70.180.4");
        factory.setUsername("admin");
        factory.setPassword("123456");
        factory.setPort(5672);

        Connection connection=factory.newConnection();
        Channel channel = connection.createChannel();

        DeliverCallback deliverCallback=(consumerTag,message)->{
            System.out.println("Get a message:"+new String(message.getBody()));
        };

        CancelCallback cancelCallback=(consumerTag)->{
            System.out.println("Interrupt consume");
        };

        channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);
    }
}
