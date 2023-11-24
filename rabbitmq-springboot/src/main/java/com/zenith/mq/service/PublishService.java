package com.zenith.mq.service;

import java.util.List;

public interface PublishService {
    String publishWithDelay(Integer ttl);

    String publishDemo();

    String confirm();

    List<String> priority();
}
