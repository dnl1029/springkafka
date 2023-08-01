package com.example.blog.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.common.KafkaFuture;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.Collections;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class KafkaHealthCheckConfig {

    private static final String topic = "kyu";
    private final KafkaAdmin kafkaAdmin;

    @Bean
    public AdminClient kafkaAdminClient() {
        return AdminClient.create(kafkaAdmin.getConfigurationProperties());
    }

    @Qualifier("kafkaHealthIndicator")
    @Bean("kafkaHealthIndicator")
    public HealthIndicator kafkaHealthIndicator(AdminClient adminClient) {
        return new AbstractHealthIndicator() {
            @Override
            protected void doHealthCheck(Health.Builder builder) throws Exception {
                KafkaFuture<TopicDescription> kafkaFuture = adminClient.describeTopics(Collections.singleton(topic)).topicNameValues().get(topic);
                try{
                    TopicDescription topicDescription = kafkaFuture.get(3000, TimeUnit.MILLISECONDS);
                    log.info("kafka server alive. topicDescription : {}",topicDescription);
                    builder.up().build();
                }
                catch (TimeoutException | ExecutionException | InterruptedException | NullPointerException e) {
                    builder.down(e).build();
                }
            }
        };
    }

}
