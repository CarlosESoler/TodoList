package com.carlos.todolistrocketseat.Data.Model;

import com.carlos.todolistrocketseat.Data.Enum.TaskStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Table(name = "tasks")
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idTask;

    @Column(length = 50)
    private String title;
    private String description;
    private LocalDate startedAt;
    private LocalDate endAt;

    @CreationTimestamp
    private LocalDateTime createdAt;

    // TODO - Make the relationship between User and Task
    private UUID idUser;

    @Enumerated(STRING)
    private TaskStatus status;
}
