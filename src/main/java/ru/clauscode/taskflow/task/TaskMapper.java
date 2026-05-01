package ru.clauscode.taskflow.task;

import java.util.Optional;

public class TaskMapper {
    public static TaskDto entityToDto(TaskEntity entity) {
        return new TaskDto(
                entity.getId().toString(),
                entity.getName(),
                entity.getDescription(),
                Optional.ofNullable(entity.getExecutor())
                        .map(it -> it.getId().toString())
                        .orElse(null),
                entity.getStatus()
        );
    }
}
