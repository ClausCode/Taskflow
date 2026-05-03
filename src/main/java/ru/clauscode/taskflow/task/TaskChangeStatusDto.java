package ru.clauscode.taskflow.task;

import jakarta.validation.constraints.NotNull;

public record TaskChangeStatusDto(
        @NotNull
        TaskStatus status
) {
}
