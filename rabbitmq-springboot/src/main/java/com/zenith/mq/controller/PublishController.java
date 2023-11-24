package com.zenith.mq.controller;

import com.zenith.mq.config.DeadDelayQueueConfig;
import com.zenith.mq.config.PluginDelayQueueConfig;
import com.zenith.mq.service.PublishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/publish")
@Slf4j
public class PublishController {

    @Autowired
    PublishService publishService;

    @GetMapping("/{ttl}")
    public String publishDiy(@PathVariable Integer ttl) {
        return publishService.publishWithDelay(ttl);
    }

    @GetMapping
    public String publish(){
        return publishService.publishDemo();
    }

    @GetMapping("/confirm")
    public String confirm(){
        return publishService.confirm();
    }

    @GetMapping("/priority")
    public List<String> priority(){
        return publishService.priority();
    }
}
