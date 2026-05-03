package ru.clauscode.taskflow.task;

import java.util.UUID;

public record TaskAssignedEvent(
        UUID taskId,
        UUID userId
) {
}
