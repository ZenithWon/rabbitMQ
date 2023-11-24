package com.zenith.mq.two;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.zenith.mq.utils.RabbitMqUtils;
import com.zenith.mq.utils.SleepUtils;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Consumer {
    public static final String QUEUE_NAME = "hello";
    public static ExecutorService executorService = Executors.newFixedThreadPool(4);


    public static void main(String[] args) throws Exception {

        for(int i=1;i<=2;i++){
            Channel channel = RabbitMqUtils.getChannel();
            channel.basicQos(1);
            String worker="Worker0"+i;
            int sleepTime = i;
            executorService.submit(()->{
                try {
                    if(sleepTime!=1){
                        channel.basicConsume(
                                QUEUE_NAME ,
                                true ,
                                RabbitMqUtils.getDefaultDeliverCallback(worker, 100) ,
                                RabbitMqUtils.getDefaultCancelCallback()
                        );
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }



    }
}
