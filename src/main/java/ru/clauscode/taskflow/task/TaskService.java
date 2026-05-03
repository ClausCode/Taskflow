package ru.clauscode.taskflow.task;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clauscode.taskflow.user.UserEntity;
import ru.clauscode.taskflow.user.UserService;

import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final TaskEventProducer taskEventProducer;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserService userService, TaskEventProducer taskEventProducer) {
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.taskEventProducer = taskEventProducer;
    }

    private Optional<TaskEntity> findEntityById(UUID id) {
        return this.taskRepository
                .findById(id);
    }

    public Optional<TaskDto> findById(UUID id) {
        return this.findEntityById(id)
                .map(TaskMapper::entityToDto);
    }

    private TaskEntity getEntityById(UUID id) {
        return this.findEntityById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task with id = " + id + " not found!"));
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

        this.taskEventProducer.sendTaskCreated(
                new TaskCreatedEvent(
                        saved.getId(),
                        saved.getName()
                )
        );

        return TaskMapper.entityToDto(saved);
    }

    public TaskDto changeStatus(UUID id, TaskStatus newStatus) {
        TaskEntity task = this.getEntityById(id);

        task.setStatus(newStatus);

        TaskEntity saved = taskRepository.save(task);
        return TaskMapper.entityToDto(saved);
    }

    public TaskDto setExecutor(UUID id, UUID executorId) {
        TaskEntity task = this.getEntityById(id);

        if (task.getStatus() == TaskStatus.COMPLETED) {
            throw new IllegalArgumentException("Task already completed");
        }

        UserEntity executor = this.userService.getEntityById(executorId);

        task.setExecutor(executor);

        TaskEntity saved = this.taskRepository.save(task);

        this.taskEventProducer.sendTaskAssigned(
                new TaskAssignedEvent(
                        saved.getId(),
                        executor.getId()
                )
        );

        return TaskMapper.entityToDto(saved);
    }
}
