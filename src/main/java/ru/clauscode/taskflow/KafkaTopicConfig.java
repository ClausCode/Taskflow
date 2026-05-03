package ru.clauscode.taskflow;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Value("${app.kafka.topic.task-assigned}")
    private String TASK_ASSIGNED_TOPIC;

    @Value("${app.kafka.topic.task-created}")
    private String TASK_CREATED_TOPIC;
    
    @Bean
    public NewTopic taskAssignedTopic() {
        return TopicBuilder
                .name(TASK_ASSIGNED_TOPIC)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic taskCreatedTopic() {
        return TopicBuilder
                .name(TASK_CREATED_TOPIC)
                .partitions(1)
                .replicas(1)
                .build();
    }
}