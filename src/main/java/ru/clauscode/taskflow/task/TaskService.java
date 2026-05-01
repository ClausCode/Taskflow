package ru.clauscode.taskflow.task;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Optional<TaskDto> findById(UUID id) {
        return this.taskRepository
                .findById(id)
                .map(TaskMapper::entityToDto);
    }

    public TaskDto getById(UUID id) {
        return this.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task with id = " + id + " not found!"));
    }

    public Page<TaskDto> getPage(Pageable pageable) {
        return this.taskRepository.findAll(pageable)
                .map(TaskMapper::entityToDto);
    }

    public TaskDto create(TaskCreateDto data) {
        TaskEntity entity = new TaskEntity();

        entity.setName(data.name());
        entity.setDescription(data.description());
        entity.setStatus(TaskStatus.PENDING);

        TaskEntity saved = this.taskRepository.save(entity);
        return TaskMapper.entityToDto(saved);
    }

    public TaskDto changeStatus(UUID id, TaskStatus newStatus) {
        TaskEntity task = this.taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task with id = " + id + " not found!"));

        task.setStatus(newStatus);

        TaskEntity saved = taskRepository.save(task);
        return TaskMapper.entityToDto(saved);
    }
}
