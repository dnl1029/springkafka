package com.example.blog.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.listener.ConsumerAwareRebalanceListener;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Slf4j
public class MyListener implements ConsumerAwareRebalanceListener {

    //파티션 재할당시 호출(서버 처음 구동시에도 호출)
    @Override
    public void onPartitionsAssigned(Consumer<?, ?> consumer, Collection<TopicPartition> partitions) {
        log.info("# PartitionsAssigned. metadata: {}, partitions: {}"
                , consumer.groupMetadata()
                , partitions);

        partitions
                .forEach(partition -> {
                    // position에 나온 offset-1 이 현재까지 발행된 offset 수이며, position 값은 이제 들어올 예정인 offset
                    log.info("partition: " + partition.partition() + ", position(readOffset): " + consumer.position(partition) + " , topic: " + partition.topic());
                });
    }
}
