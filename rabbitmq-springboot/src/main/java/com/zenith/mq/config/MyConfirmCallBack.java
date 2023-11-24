package com.zenith.mq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class MyConfirmCallBack implements RabbitTemplate.ConfirmCallback {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @PostConstruct
    //初始化bean的时候给RabbitTemplate注入该回调接口实现类
    public void init(){
        rabbitTemplate.setConfirmCallback(this);
    }

    @Override
    public void confirm(CorrelationData correlationData , boolean ack , String reason) {
        String msgId="";
        String msg="";
        if(correlationData!=null){
            msgId=correlationData.getId();
            msg=new String(correlationData.getReturnedMessage().getBody());
        }
        if(ack){
            log.info("Exchange save successfully, msgId: [{}], content: [{}]",msgId,msg);
        }else{
            log.error("Exchange save unsuccessfully, msgId: [{}], content: [{}], reason: [{}]",msgId,msg,reason);
        }
    }
}
