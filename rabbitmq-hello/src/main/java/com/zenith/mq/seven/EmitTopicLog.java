package com.zenith.mq.seven;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.zenith.mq.utils.RabbitMqUtils;

import java.util.*;

public class EmitTopicLog {
    public static final String EXCHANGE_NAME="topic_logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        Scanner scanner=new Scanner(System.in);
        while(scanner.hasNext()){
            String routingKey=scanner.nextLine();
            String msg= UUID.randomUUID().toString();
            channel.basicPublish(EXCHANGE_NAME,routingKey,null,msg.getBytes());
            System.out.println("Sending msg:"+msg+", routingKey:"+routingKey);
        }
    }
}
