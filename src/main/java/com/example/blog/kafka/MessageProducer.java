package com.example.blog.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
@Component
public class MessageProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(MessageDto messageDto) {
        kafkaTemplate.send("kyu",messageDto.getMessage()); //topic, 메시지 보내기
        log.info("sendMessage : {}",messageDto.getMessage());
    }

    public void sendMessageV2(MessageDto messageDto) {

        /* springboot 3.0 이전
        ListenableFuture<SendResult<String, String>> future_old = kafkaTemplate.send("kyu", message);
        future_old.addCallback(new ListenableFutureCallback<SendResult<String,String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("Success");
            }

            @Override
            public void onFailure(Throwable ex) {
                log.info("Failure");
            }
        });
         */

        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send("kyu", messageDto.getMessage());
        future.whenComplete(((stringStringSendResult, throwable) -> {
            if(throwable == null) {
                log.info("sendMessageV2 success, message : {}",messageDto.getMessage());
            }
            else {
                log.info("Failure",throwable.getMessage(),throwable);
            }
        }));

    }

}