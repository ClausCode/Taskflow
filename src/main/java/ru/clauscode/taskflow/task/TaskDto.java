package ru.clauscode.taskflow.task;

public record TaskDto(
        String id,
        String name,
        String description,
        String executor,
        TaskStatus status
) {
}
