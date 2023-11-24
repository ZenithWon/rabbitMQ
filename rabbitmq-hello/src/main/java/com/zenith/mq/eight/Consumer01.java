package com.zenith.mq.eight;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.zenith.mq.utils.RabbitMqUtils;
import com.zenith.mq.utils.SleepUtils;

import java.util.HashMap;
import java.util.Map;

public class Consumer01 {

    public static final String NORMAL_EXCHANGE="normal_exchange";
    public static final String DEAD_EXCHANGE="dead_exchange";

    public static final String NORMAL_QUEUE="normal_queue";
    public static final String DEAD_QUEUE="dead_queue";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMqUtils.getChannel();

        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(DEAD_EXCHANGE, BuiltinExchangeType.DIRECT);

        Map<String,Object> params=new HashMap<>();
        params.put("x-dead-letter-exchange",DEAD_EXCHANGE);
        params.put("x-dead-letter-routing-key","dead");
//        params.put("x-max-length",6);

        channel.queueDeclare(NORMAL_QUEUE,false,false,false,params);
        channel.queueDeclare(DEAD_QUEUE,false,false,false,null);


        channel.queueBind(NORMAL_QUEUE,NORMAL_EXCHANGE,"normal");
        channel.queueBind(DEAD_QUEUE,DEAD_EXCHANGE,"dead");

        DeliverCallback deliverCallback=(consumerTag, message)->{
            System.out.println("Reject message: "+new String(message.getBody()));
            channel.basicReject(message.getEnvelope().getDeliveryTag(),false);
        };

        System.out.println("Consumer01 waiting for normal queue...");
        channel.basicConsume(NORMAL_QUEUE,false,deliverCallback,tag->{});
    }
}
