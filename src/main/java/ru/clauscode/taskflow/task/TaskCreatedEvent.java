package ru.clauscode.taskflow.task;

import java.util.UUID;

public record TaskCreatedEvent(
        UUID taskId,
        String name
) {
}