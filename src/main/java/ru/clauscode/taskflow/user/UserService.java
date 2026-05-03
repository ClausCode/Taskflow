package ru.clauscode.taskflow.user;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> getAll() {
        return this.userRepository.findAll()
                .stream()
                .map(UserMapper::entityToDto)
                .toList();
    }

    public Optional<UserEntity> findEntityById(UUID id) {
        return this.userRepository
                .findById(id);
    }

    public Optional<UserDto> findById(UUID id) {
        return this.findEntityById(id)
                .map(UserMapper::entityToDto);
    }

    public Optional<UserEntity> findEntityByEmail(String email) {
        return this.userRepository
                .findByEmail(email);
    }

    public Optional<UserDto> findByEmail(String email) {
        return this.findEntityByEmail(email)
                .map(UserMapper::entityToDto);
    }

    public UserEntity getEntityById(UUID id) {
        return this.findEntityById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id = " + id + " not found!"));
    }

    public UserDto getById(UUID id) {
        return this.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id = " + id + " not found!"));
    }

    public Page<UserDto> getPage(Pageable pageable) {
        return this.userRepository.findAll(pageable)
                .map(UserMapper::entityToDto);
    }

    public UserDto create(UserCreateDto data) {
        if (findByEmail(data.email()).isPresent()) {
            throw new IllegalArgumentException("This email is already being used by another user");
        }

        UserEntity entity = new UserEntity();

        entity.setEmail(data.email());
        entity.setFullName(data.fullName());

        UserEntity saved = this.userRepository.save(entity);
        return UserMapper.entityToDto(saved);
    }
}
