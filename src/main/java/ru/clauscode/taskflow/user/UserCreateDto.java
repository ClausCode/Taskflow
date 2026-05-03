package ru.clauscode.taskflow.user;

public record UserCreateDto(
        String fullName,
        String email
) {
}
