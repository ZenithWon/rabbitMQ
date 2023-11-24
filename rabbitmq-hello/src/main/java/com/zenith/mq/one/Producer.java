package com.zenith.mq.one;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.zenith.mq.utils.RabbitMqUtils;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Producer {
    public static final String QUEUE_NAME="hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        Scanner sc=new Scanner(System.in);
        while (true){
            System.out.printf("Sending: ");
            String msg=sc.nextLine();
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
        }

    }
}
