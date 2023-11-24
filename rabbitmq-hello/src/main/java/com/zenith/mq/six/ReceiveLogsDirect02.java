package com.zenith.mq.six;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.zenith.mq.utils.RabbitMqUtils;

import java.io.IOException;

public class ReceiveLogsDirect02 {
    public static final String EXCHANGE_NAME="direct_logs";

    public static void main(String[] args) throws IOException {
        Channel channel = RabbitMqUtils.getChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        channel.queueDeclare("disk",false,false,false,null);

        channel.queueBind("disk",EXCHANGE_NAME,"error");

        channel.basicConsume(
                "disk",
                true,
                RabbitMqUtils.getDefaultDeliverCallback("ReceiveLogsDirect02",1),
                RabbitMqUtils.getDefaultCancelCallback()
        );
    }
}
