package com.zenith.mq.seven;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.zenith.mq.utils.RabbitMqUtils;

import java.io.IOException;

public class ReceiveLogsTopic01 {
    public static final String EXCHANGE_NAME="topic_logs";

    public static void main(String[] args) throws IOException {
        Channel channel = RabbitMqUtils.getChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName,EXCHANGE_NAME,"*.orange.*");

        channel.basicConsume(
                queueName,
                true,
                RabbitMqUtils.getDefaultDeliverCallback("ReceiveLogsTopic01",1),
                RabbitMqUtils.getDefaultCancelCallback()
        );
    }
}
