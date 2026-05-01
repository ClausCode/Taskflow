package ru.clauscode.taskflow.task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController()
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public Page<TaskDto> getTaskPage(Pageable pageable) {
        return this.taskService.getPage(pageable);
    }

    @GetMapping("/{id}")
    public TaskDto getTaskById(@PathVariable UUID id) {
        return this.taskService.getById(id);
    }

    @PostMapping
    public TaskDto createTask(@RequestBody TaskCreateDto data) {
        return this.taskService.create(data);
    }

    @PatchMapping("/{id}/status")
    public TaskDto changeStatus(
            @PathVariable UUID id,
            @RequestBody TaskChangeStatusDto data
    ) {
        return this.taskService.changeStatus(id, data.status());
    }
}
