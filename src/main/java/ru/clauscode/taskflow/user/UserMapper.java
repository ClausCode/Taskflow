package ru.clauscode.taskflow.user;

public class UserMapper {
    public static UserDto entityToDto(UserEntity entity) {
        return new UserDto(
                entity.getId().toString(),
                entity.getFullName(),
                entity.getEmail()
        );
    }
}
