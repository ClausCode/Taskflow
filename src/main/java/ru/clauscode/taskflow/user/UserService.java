package ru.clauscode.taskflow.user;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
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

    public Optional<UserDto> findById(String id) {
        return this.userRepository
                .findById(UUID.fromString(id))
                .map(UserMapper::entityToDto);
    }

    public UserDto getById(String id) {
        return this.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found!"));
    }
}
