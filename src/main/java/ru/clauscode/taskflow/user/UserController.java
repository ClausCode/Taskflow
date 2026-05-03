package ru.clauscode.taskflow.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController()
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Page<UserDto> getUserPage(Pageable pageable) {
        return this.userService.getPage(pageable);
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable UUID id) {
        return this.userService.getById(id);
    }

    @PostMapping
    public UserDto createUser(@RequestBody UserCreateDto data) {
        return this.userService.create(data);
    }
}
