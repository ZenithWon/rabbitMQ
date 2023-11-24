package com.zenith.mq.listener;

import com.rabbitmq.client.Channel;
import com.zenith.mq.config.DeadDelayQueueConfig;
import com.zenith.mq.config.PluginDelayQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class DelayConsumer {
    @RabbitListener(queues = PluginDelayQueueConfig.DELAY_QUEUE_NAME)
    public void delayMsgHandler(Message message, Channel channel) throws Exception{
        String msg=new String(message.getBody());
        String consumerQueue = message.getMessageProperties().getConsumerQueue();

        log.info("Queue [{}] receive message=>[{}]",consumerQueue,msg);
    }
}
