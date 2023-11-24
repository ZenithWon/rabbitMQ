package com.zenith.mq.listener;

import com.rabbitmq.client.Channel;
import com.zenith.mq.config.BackupMqConfig;
import com.zenith.mq.config.ConfirmMqConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class BackupConsumer {
    @RabbitListener(queues = BackupMqConfig.BACKUP_QUEUE_NAME)
    public void msgHandler1(Message message, Channel channel) throws Exception{
        String msg=new String(message.getBody());
        String consumerQueue = message.getMessageProperties().getConsumerQueue();

        log.warn("Queue [{}] receive message=>[{}]",consumerQueue,msg);
    }

    @RabbitListener(queues = BackupMqConfig.WARN_QUEUE_NAME)
    public void msgHandler2(Message message, Channel channel) throws Exception{
        String msg=new String(message.getBody());
        String consumerQueue = message.getMessageProperties().getConsumerQueue();

        log.warn("Queue [{}] receive message=>[{}]",consumerQueue,msg);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),true);
    }
}
