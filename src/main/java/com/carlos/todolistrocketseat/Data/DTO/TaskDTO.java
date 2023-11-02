package com.carlos.todolistrocketseat.Data.DTO;

import com.carlos.todolistrocketseat.Data.Enum.TaskStatus;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.UUID;

public record TaskDTO(
            UUID idTask,
            UUID idUser,
            @Length(max = 50)
            String title,
            String description,
            LocalDate createdAt,
            LocalDate endAt,
            LocalDate startedAt,
            TaskStatus status
) {
}
