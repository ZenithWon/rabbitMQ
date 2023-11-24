package com.zenith.mq.listener;

import com.rabbitmq.client.Channel;
import com.zenith.mq.config.DeadDelayQueueConfig;
import com.zenith.mq.config.PriorityMqConfig;
import com.zenith.mq.utils.SleepUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class Consumer {

    @RabbitListener(queues = DeadDelayQueueConfig.DELAY_QUEUE_HANDLER)
    public void delayMsgHandler(Message message, Channel channel) throws Exception{
        String msg=new String(message.getBody());
        String consumerQueue = message.getMessageProperties().getConsumerQueue();

        log.info("Queue [{}] receive message=>[{}]",consumerQueue,msg);

    }

    @RabbitListener(queues = PriorityMqConfig.QUEUE_NAME)
    public void priorityMsgHandler(Message message, Channel channel) throws Exception{
        String msg=new String(message.getBody());
        String consumerQueue = message.getMessageProperties().getConsumerQueue();

        SleepUtil.sleep(1);
        log.info("Queue [{}] receive message=>[{}]",consumerQueue,msg);
    }
}
