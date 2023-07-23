package com.example.blog.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisConsumer implements MessageListener {

    public static List<String> messageList = new ArrayList<>();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try{
            RedisMessageDto redisMessageDto = mapper.readValue(message.getBody(), RedisMessageDto.class);
            messageList.add(message.toString());

            log.info("parameter message : {}",message.toString());
            log.info("messageList : {}",messageList);

        }
        catch (Exception e){
            log.error("redis consume error",e.getMessage(),e);
        }
    }
}
