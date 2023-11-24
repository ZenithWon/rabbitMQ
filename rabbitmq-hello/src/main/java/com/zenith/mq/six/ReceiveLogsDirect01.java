package com.zenith.mq.six;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.zenith.mq.utils.RabbitMqUtils;

import java.io.IOException;

public class ReceiveLogsDirect01 {
    public static final String EXCHANGE_NAME="direct_logs";

    public static void main(String[] args) throws IOException {
        Channel channel = RabbitMqUtils.getChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        channel.queueDeclare("console",false,false,false,null);

        channel.queueBind("console",EXCHANGE_NAME,"debug");
        channel.queueBind("console",EXCHANGE_NAME,"info");

        channel.basicConsume(
                "console",
                true,
                RabbitMqUtils.getDefaultDeliverCallback("ReceiveLogsDirect01",1),
                RabbitMqUtils.getDefaultCancelCallback()
        );
    }
}
