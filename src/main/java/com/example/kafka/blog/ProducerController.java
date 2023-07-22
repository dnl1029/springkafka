package com.example.kafka.blog;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
@Slf4j
public class ProducerController {

    private final MessageProducer messageProducer;

    @PostMapping("kafka/test")
    public void sendMessage(@RequestBody MessageDto messageDto){
        log.info("controller");
        messageProducer.sendMessage(messageDto);
        messageProducer.sendMessageV2(messageDto);

    }
}