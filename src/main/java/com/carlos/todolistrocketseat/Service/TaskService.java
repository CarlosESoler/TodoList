package com.carlos.todolistrocketseat.Service;

import com.carlos.todolistrocketseat.Model.Task;
import com.carlos.todolistrocketseat.Repository.TaskRepository;
import com.carlos.todolistrocketseat.Utils.UtilsProp;
import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity updateTask(@RequestBody Task task, @PathVariable UUID idTask, HttpServletRequest request) {
        Optional<Task> taskT = taskRepository.findById(idTask);
        if (taskT.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Tarefa não encontrada");
        }

        Object idUser = request.getAttribute("idUser");

        if (!taskT.get().getIdUser().equals(idUser))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Você não tem permissão para alterar essa tarefa");

        UtilsProp.copyNonNullProperties(task, taskT.get());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(taskRepository.save(taskT.get()));
    }

    public ResponseEntity getTaskById(@PathVariable UUID idTask, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(taskRepository.findById(idTask));
    }

    public ResponseEntity getTasks(HttpServletRequest request) {
        List<Task> tasks = taskRepository
                .findByIdUser((UUID) request.getAttribute("idUser"));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tasks);
    }

    public ResponseEntity createTask(@RequestBody Task task, HttpServletRequest request) {
        verifyDate(task);

        task.setIdUser((UUID) request.getAttribute("idUser"));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(taskRepository.save(task));
    }

    private void verifyDate(Task task) {
        LocalDate today = LocalDate.now();
        LocalDate startedAt = task.getStartedAt();
        LocalDate endAt = task.getEndAt();

        if (startedAt.isBefore(today) || endAt.isBefore(today)) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A data de início / término não pode ser menor que a data atual");
        }
        if (startedAt.isAfter(task.getEndAt())) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A data de início não pode ser maior que a data de término");
        }
    }
}


