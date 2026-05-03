package ru.clauscode.taskflow.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TaskEventProducer {
    @Value("${app.kafka.topic.task-created}")
    private String taskCreatedTopic;

    @Value("${app.kafka.topic.task-assigned}")
    private String taskAssignedTopic;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final Logger log = LoggerFactory.getLogger(TaskEventProducer.class);

    public TaskEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendTaskCreated(TaskCreatedEvent event) {
        this.log.info("Send event to kafka(" + taskCreatedTopic + ")", event);
        kafkaTemplate.send(taskCreatedTopic, event);
    }

    public void sendTaskAssigned(TaskAssignedEvent event) {
        this.log.info("Send event to kafka(" + taskAssignedTopic + ")", event);
        kafkaTemplate.send(taskAssignedTopic, event);
    }
}
