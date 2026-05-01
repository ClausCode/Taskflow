package ru.clauscode.taskflow.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.clauscode.taskflow.task.TaskEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "fullname")
    private String fullName;

    @Column(name = "email")
    private String email;

    @OneToMany(
            mappedBy = "executor",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<TaskEntity> taskList = new ArrayList<>();
}
