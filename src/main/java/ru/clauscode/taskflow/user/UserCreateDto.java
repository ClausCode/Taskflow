package ru.clauscode.taskflow.user;

import jakarta.validation.constraints.NotNull;

public record UserCreateDto(
        @NotNull
        String fullName,
        @NotNull
        String email
) {
}
