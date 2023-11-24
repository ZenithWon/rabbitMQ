package com.zenith.mq.four;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.rabbitmq.client.MessageProperties;
import com.zenith.mq.utils.RabbitMqUtils;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConfirmPublish {

    public static void main(String[] args) throws IOException {
        Channel channel = RabbitMqUtils.getChannel();
        String queueName= UUID.randomUUID().toString();

        channel.queueDeclare(queueName,true,false,false,null);
        channel.confirmSelect();

        ConcurrentSkipListMap<Long,String> confirmMap=new ConcurrentSkipListMap<>();

        ConfirmCallback ackCallback=(deliveryTag,multiple)->{
            if(multiple){
                ConcurrentNavigableMap<Long, String> confirmed = confirmMap.headMap(deliveryTag);
                confirmed.clear();
            }else{
                confirmMap.remove(deliveryTag);
            }

            System.out.println("Publish successfully, message=>["+deliveryTag+"]");
        };

        ConfirmCallback nAckCallback=(deliveryTag,multiple)->{
            System.out.println("Publish unsuccessfully, message=>["+deliveryTag+"]");
            System.out.println("No ack msg:"+confirmMap.toString());
        };



        long begin=System.currentTimeMillis();
        channel.addConfirmListener(ackCallback,nAckCallback);

        for(int i=1;i<=1000;i++){
            String msg="msg "+i;
            channel.basicPublish("",queueName, MessageProperties.PERSISTENT_TEXT_PLAIN ,msg.getBytes());
            confirmMap.put(channel.getNextPublishSeqNo(),msg);
        }

        long end=System.currentTimeMillis();
        System.out.println("Consume time: "+(end-begin)+"ms");
    }
}
