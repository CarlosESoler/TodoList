package com.carlos.todolistrocketseat.Domain.Service;

import com.carlos.todolistrocketseat.Data.Model.Task;
import com.carlos.todolistrocketseat.Domain.Repository.TaskRepository;
import com.carlos.todolistrocketseat.Exceptions.InvalidDate.InvalidDateException;
import com.carlos.todolistrocketseat.Utils.UtilsProp;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;
    public ResponseEntity updateTask(@RequestBody Task taskDTO, @PathVariable UUID idTask, HttpServletRequest request) {
        Optional<Task> taskToUpdate = taskRepository.findById(idTask);

        if (taskToUpdate.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Tarefa não encontrada");
        }

        Object idUser = request.getAttribute("idUser");

        if (!taskToUpdate.get().getIdUser().equals(idUser))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Você não tem permissão para alterar essa tarefa");

        UtilsProp.copyNonNullProperties(taskDTO, taskToUpdate.get());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(taskRepository.save(taskToUpdate.get()));
    }

    public ResponseEntity getTaskById(@PathVariable UUID idTask) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(taskRepository.findById(idTask));
    }

    public ResponseEntity getTasks(HttpServletRequest request) {
        List<Task> tasks = taskRepository
                .findByIdUser((UUID) request.getAttribute("idUser"));
        if(tasks.isEmpty())
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Nenhuma tarefa encontrada");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tasks);
    }

    public ResponseEntity createTask(@RequestBody Task taskDTO, HttpServletRequest request) {
        verifyDate(taskDTO);

        taskDTO.setIdUser((UUID) request.getAttribute("idUser"));
        Task task = new Task();
        BeanUtils.copyProperties(taskDTO, task);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(taskRepository.save(task));
    }

    private void verifyDate(Task task) throws InvalidDateException {
        LocalDate today = LocalDate.now();
        LocalDate startedAt = task.getStartedAt();
        LocalDate endAt = task.getEndAt();

        if (startedAt.isBefore(today) || endAt.isBefore(today)) {
            throw new InvalidDateException("A data de início e término não podem ser menores que a data atual");
        }
        if (startedAt.isAfter(task.getEndAt())) {
            throw new InvalidDateException("A data de início não pode ser maior que a data de término");
        }
    }
}


