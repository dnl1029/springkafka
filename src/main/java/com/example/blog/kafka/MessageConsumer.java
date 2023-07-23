package com.example.blog.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageConsumer {

    @KafkaListener(topics = "kyu", groupId = "groupKyu")
    public void receiveMessage(ConsumerRecord<String, String> record){
        log.info("Consume message : {}", record.value());
    }

}
