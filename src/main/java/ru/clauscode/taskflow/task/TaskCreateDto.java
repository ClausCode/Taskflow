package ru.clauscode.taskflow.task;

import jakarta.validation.constraints.NotNull;

public record TaskCreateDto(
        @NotNull
        String name,
        @NotNull
        String description
) {
}
