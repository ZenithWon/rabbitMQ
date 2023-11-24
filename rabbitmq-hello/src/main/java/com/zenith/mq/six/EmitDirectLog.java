package com.zenith.mq.six;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.zenith.mq.utils.RabbitMqUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class EmitDirectLog {
    public static final String EXCHANGE_NAME="direct_logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        Map<Integer,String> logMap=new HashMap<>();
        logMap.put(0,"debug");
        logMap.put(1,"info");
        logMap.put(2,"error");

        for(int i=0;i<10;i++){
            String model=logMap.get(new Random().nextInt(3));

            String msg= model+" message:"+UUID.randomUUID().toString();
            channel.basicPublish(EXCHANGE_NAME,model,null,msg.getBytes());
            System.out.println("Sending msg:"+msg);
            Thread.sleep(500);
        }
    }
}
