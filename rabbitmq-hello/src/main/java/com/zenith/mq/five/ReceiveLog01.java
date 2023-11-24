package com.zenith.mq.five;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.zenith.mq.utils.RabbitMqUtils;

import java.io.IOException;

public class ReceiveLog01 {
    public static final String EXCHANGE_NAME="logs";

    public static void main(String[] args) throws IOException {
        Channel channel = RabbitMqUtils.getChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        String queue = channel.queueDeclare().getQueue();
        channel.queueBind(queue,EXCHANGE_NAME,"");

        channel.basicConsume(
                queue,
                true,
                RabbitMqUtils.getDefaultDeliverCallback("ReceiveLog01",1),
                RabbitMqUtils.getDefaultCancelCallback()
        );
    }
}
