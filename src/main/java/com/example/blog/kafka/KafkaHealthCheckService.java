package com.example.blog.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaHealthCheckService {

    @Qualifier("kafkaHealthIndicator")
    private final HealthIndicator kafkaHealthIndicator;

    @Scheduled(fixedDelay = 5000L)
    public void kafkaHealthCheck() {
        Health health = kafkaHealthIndicator.health();
        log.info("health.getDetails : {}, health.getStatus : {}"
                ,health.getDetails()
                ,health.getStatus());
    }

}
