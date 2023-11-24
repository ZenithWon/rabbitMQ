package com.zenith.mq.three;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.zenith.mq.utils.RabbitMqUtils;
import com.zenith.mq.utils.SleepUtils;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Work01 {
    public static final String QUEUE_NAME = "hello";

    public static DeliverCallback getDeliverCallback(Channel channel){
        return (consumerTag, message)->{
            SleepUtils.sleep(1);
            System.out.println("Work01 deliver message: "+new String(message.getBody()));
            channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
        };
    }

    public static CancelCallback getCancelCallback(){
        return (consumerTag)->{
            System.out.println("Interrupt consume");
        };
    }

    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMqUtils.getChannel();
        channel.basicQos(1);
        channel.basicConsume(
                QUEUE_NAME ,
                false ,
                getDeliverCallback(channel) ,
                getCancelCallback()
        );
    }
}
