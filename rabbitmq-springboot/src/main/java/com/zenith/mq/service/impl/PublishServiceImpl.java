package com.zenith.mq.service.impl;

import com.zenith.mq.config.ConfirmMqConfig;
import com.zenith.mq.config.DeadDelayQueueConfig;
import com.zenith.mq.config.PluginDelayQueueConfig;
import com.zenith.mq.config.PriorityMqConfig;
import com.zenith.mq.service.PublishService;
import com.zenith.mq.utils.MessageTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@Slf4j
public class PublishServiceImpl implements PublishService {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Override
    public String publishWithDelay(Integer ttl) {
        String logMsg = MessageTools.sendRandomMessageWithTtl(
                rabbitTemplate ,
                PluginDelayQueueConfig.DELAY_EXCHANGE_NAME ,
                PluginDelayQueueConfig.ROUTING_KEY_NAME ,
                ttl
        );

        log.info(logMsg);
        return logMsg;
    }

    @Override
    public String publishDemo() {
        String result="";

        String logMsg1=MessageTools.sendRandomMessage(
                rabbitTemplate,
                DeadDelayQueueConfig.NORMAL_EXCHANGE,
                DeadDelayQueueConfig.BINDING_NORMAL_DEFAULT
        );
        result+=logMsg1;
        log.info(logMsg1);
        result+="\n";

        String logMsg2=MessageTools.sendRandomMessage(
                rabbitTemplate,
                DeadDelayQueueConfig.NORMAL_EXCHANGE,
                DeadDelayQueueConfig.BINDING_NORMAL_PLUS
        );
        result+=logMsg2;
        log.info(logMsg2);

        return result;
    }

    @Override
    public String confirm() {
        String logMsg= MessageTools.sendRandomMessage(
                rabbitTemplate,
                ConfirmMqConfig.EXCHANGE_NAME,
                ConfirmMqConfig.ROUTING_KEY_NAME
        );

        log.info(logMsg);
        return logMsg;
    }

    @Override
    public List<String> priority() {

        List<String> priorityList=new ArrayList<>();

        for(int i=0;i<10;i++){
            Integer priority = new Random().nextInt(10);
            String logMsg= MessageTools.sendRandomMessageWithPriority(
                    rabbitTemplate,
                    PriorityMqConfig.EXCHANGE_NAME,
                    PriorityMqConfig.ROUTING_KEY_NAME,
                    priority
            );
            log.info(logMsg);
            priorityList.add(logMsg);
        }



        return priorityList;
    }
}
