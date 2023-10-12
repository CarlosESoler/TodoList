package com.carlos.todolistrocketseat.Controller;

import com.carlos.todolistrocketseat.Model.Task;
import com.carlos.todolistrocketseat.Repository.TaskRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @PostMapping
    public ResponseEntity createTask(@RequestBody Task task, HttpServletRequest request) {

        LocalDate today = LocalDate.now();
        LocalDate startedAt = task.getStartedAt();
        LocalDate endAt = task.getEndAt();

        if(startedAt.isBefore(today) || endAt.isBefore(today)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de início / término não pode ser menor que a data atual");
        }
        if(startedAt.isAfter(task.getEndAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de início não pode ser maior que a data de término");
        }

        task.setIdUser((UUID) request.getAttribute("idUser"));
        return ResponseEntity.status(HttpStatus.CREATED).body(taskRepository.save(task));
    }

    @GetMapping
    public ResponseEntity getTasks(HttpServletRequest request) {
        List<Task> tasks = taskRepository.findByIdUser((UUID) request.getAttribute("idUser"));
        System.out.println(tasks);
        return ResponseEntity.status(HttpStatus.OK).body(tasks);
    }

    @GetMapping("/{idTask}")
    public ResponseEntity getTaskById(@PathVariable UUID idTask, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(taskRepository.findById(idTask));
    }
}
