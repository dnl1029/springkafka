package com.example.blog.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api")
public class RedisController {

    private final RedisProducer redisProducer;

    @PostMapping("redis/test")
    public void sendRedisMessage(@RequestBody RedisMessageDto redisMessageDto) {
        redisProducer.sendMessage(redisMessageDto);
    }

}
