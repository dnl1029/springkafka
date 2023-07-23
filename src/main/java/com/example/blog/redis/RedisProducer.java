package com.example.blog.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisProducer {

    private final RedisTemplate<String, Object> redisTemplate;

    public void sendMessage(RedisMessageDto redisMessageDto) {
        redisTemplate.convertAndSend("kyu", redisMessageDto);
        log.info("send message : {}", redisMessageDto);
    }

}
